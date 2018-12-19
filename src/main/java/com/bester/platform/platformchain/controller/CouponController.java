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
import org.joda.time.DateTime;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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

    /**
     * 用户领取优惠券
     *
     * @param couponId
     * @return
     */
    @GetMapping("/user/receive/coupon")
    public CommonResult receiveCoupon(int couponId) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        CouponDTO couponDTO = couponService.inquireCouponById(couponId);
        if (couponDTO == null) {
            return CommonResult.fail(403, "参数错误");
        }
        int userId = UserInfoUtil.getUserId();
        UserInfoDTO userInfo = userInfoService.findUserInfoByUserId(userId);
        if (couponDTO.getVipLevel() > userInfo.getVip()) {
            return CommonResult.fail(403, "等级不够");
        }
        if(couponDTO.getLimitNum()<=userCouponService.findCouponCountById(userId,couponDTO.getId())){
            return CommonResult.fail(403,"领取已达上限");
        }
        if (couponDTO.getMargin() <= 0) {
            return CommonResult.fail(404, "优惠券已抢光");
        }
        UserCouponDTO userCouponDTO = new UserCouponDTO();
        userCouponDTO.setShopId(couponDTO.getShopId());
        userCouponDTO.setUserId(userId);
        userCouponDTO.setCouponId(couponId);
        userCouponDTO.setStatus(2);
        DateTime today = new DateTime();
        Date failureTime = today.plusDays(couponDTO.getValidityPeriod()).toDate();
        Date failureDate = sdf.parse(sdf.format(failureTime));
        userCouponDTO.setFailureTime(failureDate);

        int affectCount = userCouponService.receiveCoupon(userCouponDTO);
        if(affectCount==0){
            return CommonResult.fail(500,"领取失败");
        }
        return CommonResult.success("200");
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
        return CommonResult.success(couponList);
    }


}
