package com.bester.platform.platformchain.service;

import java.util.List;

/**
 * @author zhangqiang
 * @date 2018-12-18
 */

public interface UserCouponService {

    /**
     * 查找用户已使用和未使用的优惠券ID
     *
     * @param userId
     * @param status
     * @return
     */
    List<Integer> findUnusedAndUsedCouponId(Integer userId, Integer status);

    /**
     * 查找用户已过期的优惠券ID
     *
     * @param userId
     * @return
     */
    List<Integer> findExpiredCoupon(Integer userId);

}
