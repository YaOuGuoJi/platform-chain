package com.bester.platform.platformchain.controller;

import com.bester.platform.platformchain.common.CommonResult;
import com.bester.platform.platformchain.dto.UserCouponDTO;
import com.bester.platform.platformchain.service.CouponService;
import com.bester.platform.platformchain.service.UserCouponService;
import com.bester.platform.platformchain.util.UserInfoUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
    @GetMapping("user/receive/coupon")
    public CommonResult receiveCoupon(int couponId){
        UserCouponDTO userCouponDTO=new UserCouponDTO();
        userCouponDTO.setUserId(UserInfoUtil.getUserId());
        userCouponDTO.setCouponId(couponId);
        userCouponDTO.setStatus(2);

        return null;
    }
}
