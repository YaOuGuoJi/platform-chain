package com.bester.platform.platformchain.controller;

import com.bester.platform.platformchain.common.CommonResult;
import com.bester.platform.platformchain.constant.Coupon;
import com.bester.platform.platformchain.dto.CouponDTO;
import com.bester.platform.platformchain.enums.HttpStatus;
import com.bester.platform.platformchain.service.CouponService;
import com.bester.platform.platformchain.service.UserCouponService;
import com.bester.platform.platformchain.util.UserInfoUtil;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author zhangqiang
 * @date 2018-12-18
 */
@RestController
public class CouponController {

    @Resource
    private UserCouponService userCouponService;

    @Resource
    private CouponService couponService;

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
