package com.bester.platform.platformchain.controller;

import com.bester.platform.platformchain.common.CommonResult;
import com.bester.platform.platformchain.dto.CouponDTO;
import com.bester.platform.platformchain.service.CouponService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @PostMapping("/coupon/add")
    public CommonResult addCoupon(@RequestParam CouponDTO couponDTO) {

        return new CommonResult();
    }

}
