package com.bester.platform.platformchain.dao;

import com.bester.platform.platformchain.entity.UserCouponEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhangqiang
 * @date 2018-12-18
 */

public interface UserCouponDao {

    /**
     * 查找用户已使用和未使用的优惠券ID
     *
     * @param userId
     * @param status
     * @return
     */
    List<Integer> findUnusedAndUsedCouponId(@Param("userId") Integer userId,
                                   @Param("status") Integer status);

    /**
     * 查找用户已过期的优惠券ID
     *
     * @param userId
     * @return
     */
    List<Integer> findExpiredCoupon(@Param("userId") Integer userId);

    /**
     * 用户领取优惠券
     *
     * @param userCoupon
     * @return
     */
    int receiveCoupon(@Param("userCoupon") UserCouponEntity userCoupon);
}
