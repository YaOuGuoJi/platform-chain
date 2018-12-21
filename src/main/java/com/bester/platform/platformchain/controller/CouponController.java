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
import com.google.common.collect.Maps;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author zhangqiang
 * @date 2018-12-18
 */
@RestController
public class CouponController {
    @Resource
    private CouponService couponService;
    @Resource
    private UserCouponService userCouponService;
    @Resource
    private UserInfoService userInfoService;
    private static final Logger LOGGER = LoggerFactory.getLogger(CouponController.class);

    /**
     * 用户领取优惠券
     *
     * @param couponId
     * @return
     */
    @GetMapping("/user/receive/coupon")
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
        UserCouponDTO userCouponDTO = new UserCouponDTO();
        userCouponDTO.setShopId(couponDTO.getShopId());
        userCouponDTO.setUserId(userId);
        userCouponDTO.setCouponId(couponId);
        DateTime today = new DateTime();
        Date failureTime = today.plusDays(couponDTO.getValidityPeriod()).toDate();
        userCouponDTO.setFailureTime(failureTime);
        int affectCount = userCouponService.receiveCoupon(userCouponDTO, couponDTO.getMargin());
        if (affectCount == 0) {
            LOGGER.error("用户" + userInfo.getUserId() + "领取优惠券" + couponDTO.getId() + "失败");
            return CommonResult.fail(500, "领取失败,服务器异常");
        }
        return CommonResult.success();
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
        List<Integer> couponIdList;
        if (Objects.equals(status, Coupon.EXPIRED)) {
            couponIdList = userCouponService.findExpiredCoupon(userId);
        } else {
            couponIdList = userCouponService.findUnusedAndUsedCouponId(userId, status);
        }
        if (CollectionUtils.isEmpty(couponIdList)) {
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        List<CouponDTO> couponList = new ArrayList<>();
        couponIdList.forEach(couponId -> {
            CouponDTO couponDTO = couponService.inquireCouponById(couponId);
            if (couponDTO != null) {
                couponList.add(couponDTO);
            }
        });
        Map<String, List<CouponDTO>> data = Maps.newHashMap();
        data.put("couponInfoList", couponList);
        return CommonResult.success(data);
    }

}
