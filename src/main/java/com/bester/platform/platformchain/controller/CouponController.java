package com.bester.platform.platformchain.controller;

import com.bester.platform.platformchain.common.CommonResult;
import com.bester.platform.platformchain.constant.Coupon;
import com.bester.platform.platformchain.dto.CouponDTO;
import com.bester.platform.platformchain.dto.UserCouponDTO;
import com.bester.platform.platformchain.dto.UserInfoDTO;
import com.bester.platform.platformchain.enums.HttpStatus;
import com.bester.platform.platformchain.service.CouponService;
import com.bester.platform.platformchain.service.UserCouponService;
import com.bester.platform.platformchain.service.UserInfoService;
import com.bester.platform.platformchain.util.UserInfoUtil;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhangqiang
 * @date 2018-12-18
 */
@RestController
public class CouponController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CouponController.class);
    @Resource
    private CouponService couponService;
    @Resource
    private UserCouponService userCouponService;
    @Resource
    private UserInfoService userInfoService;

    /**
     * 优惠券分页列表
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/coupon/list")
    public CommonResult getUserCouponInfo(int pageNum, int pageSize) {
        int userId = UserInfoUtil.getUserId();
        if (userId <= 0 || pageNum <= 0 || pageSize <= 0) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        PageInfo<CouponDTO> couponDTOPageInfo = couponService.queryAllCouponInfo(pageNum, pageSize);
        if (couponDTOPageInfo == null) {
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        List<CouponDTO> couponList = couponDTOPageInfo.getList();
        List<Integer> couponIdList = couponList.stream().map(CouponDTO::getId).collect(Collectors.toList());
        Map<Integer, Integer> couponUserCount = userCouponService.selectCouponCount(userId, couponIdList);
        couponList.forEach(couponDTO -> {
            int remainNum = couponDTO.getLimitNum() - couponUserCount.getOrDefault(couponDTO.getId(), 0);
            couponDTO.setLimitNum(remainNum);
        });
        return CommonResult.success(couponDTOPageInfo);
    }

    /**
     * 查找用户已使用、未使用、已过期优惠券列表
     *
     * @param status
     * @return
     */
    @GetMapping("/coupon/status")
    public CommonResult inquireCouponStatus(Integer status) {
        int userId = UserInfoUtil.getUserId();
        if (userId <= 0) {
            return CommonResult.fail(HttpStatus.UNAUTHORIZED);
        }
        if (status < 0) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        List<UserCouponDTO> UserCouponList;
        if (Objects.equals(status, Coupon.EXPIRED)) {
            UserCouponList = userCouponService.findExpiredCoupon(userId);
        } else {
            UserCouponList = userCouponService.findUnusedAndUsedCoupon(userId, status);
        }
        if (CollectionUtils.isEmpty(UserCouponList)) {
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        List<Map<String, Object>> couponList = new ArrayList<>();
        UserCouponList.forEach(userCoupon -> {
            if (userCoupon.getCouponId() != null) {
                CouponDTO couponDTO = couponService.inquireCouponById(userCoupon.getCouponId());
                if (couponDTO != null) {
                    Map<String, Object> myCoupon = new HashMap<>(2);
                    myCoupon.put("userCoupon", userCoupon);
                    myCoupon.put("couponInfo", couponDTO);
                    couponList.add(myCoupon);
                }
            }
        });
        return CommonResult.success(couponList);
    }

    /**
     * 用户领取优惠券
     *
     * @param couponId
     * @return
     */
    @PostMapping("/user/receive/coupon")
    public CommonResult receiveCoupon(Integer couponId) {
        if (couponId == null) {
            return CommonResult.fail(403, "参数错误");
        }
        CouponDTO couponDTO = couponService.inquireCouponById(couponId);
        if (couponDTO == null) {
            return CommonResult.fail(403, "参数错误");
        }
        int userId = UserInfoUtil.getUserId();
        UserInfoDTO userInfo = userInfoService.findUserInfoByUserId(userId);
        if (couponDTO.getVipLevel() > userInfo.getVip()) {
            return CommonResult.fail(403, "等级不够");
        }
        int couponCount = userCouponService.findCouponCountById(userId, couponDTO.getId());
        if (couponDTO.getLimitNum() <= couponCount) {
            return CommonResult.fail(403, "领取已达上限");
        }
        if (couponDTO.getMargin() <= 0) {
            return CommonResult.fail(404, "优惠券已抢光");
        }
        int affectCount = userCouponService.receiveCoupon(userId, couponId);
        if (affectCount == 0) {
            LOGGER.error("领取优惠券失败！userId: [{}], couponId: [{}]", userId, couponId);
            return CommonResult.fail(500, "领取失败,服务器异常");
        }
        return CommonResult.success();
    }

}
