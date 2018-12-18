package com.bester.platform.platformchain.service;

import com.bester.platform.platformchain.dto.CouponDTO;

/**
 * @author zhangqiang
 * @date 2018-12-18
 */

public interface CouponService {

    /**
     * 添加优惠券
     *
     * @param couponDTO
     * @return
     */
    int addCoupon(CouponDTO couponDTO);

}
