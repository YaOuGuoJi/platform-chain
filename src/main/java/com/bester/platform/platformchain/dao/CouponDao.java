package com.bester.platform.platformchain.dao;

import com.bester.platform.platformchain.entity.CouponEntity;
import org.apache.ibatis.annotations.Param;

/**
 * @author zhangqiang
 * @date 2018-12-18
 */

public interface CouponDao {

    /**
     * 添加优惠券
     *
     * @param coupon
     * @return
     */
    int addCoupon(@Param("coupon") CouponEntity coupon);

}
