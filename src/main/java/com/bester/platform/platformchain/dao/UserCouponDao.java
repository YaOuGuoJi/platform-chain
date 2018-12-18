package com.bester.platform.platformchain.dao;

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

}
