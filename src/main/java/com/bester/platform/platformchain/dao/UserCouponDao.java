package com.bester.platform.platformchain.dao;

import com.bester.platform.platformchain.entity.UserCouponEntity;
import org.apache.ibatis.annotations.Param;

/**
 * @author zhangqiang
 * @date 2018-12-18
 */

public interface UserCouponDao {
    /**
     * 用户领取优惠券
     *
     * @param userCoupon
     * @return
     */
    int receiveCoupon(@Param("userCoupon") UserCouponEntity userCoupon);
}
