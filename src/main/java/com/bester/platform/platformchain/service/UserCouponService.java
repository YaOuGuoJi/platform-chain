package com.bester.platform.platformchain.service;

import com.bester.platform.platformchain.dto.UserCouponDTO;

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

    /**
     * 用户领取优惠券
     * @param userCouponDTO
     * @param couponNum
     * @return
     */
    int receiveCoupon(UserCouponDTO userCouponDTO, int couponNum);

    /**
     * 根据用户的id和优惠券id查询该用户领取该类型优惠券的数量
     *
     * @param userId
     * @param couponId
     * @return
     */
    int findCouponCountById(Integer userId, Integer couponId);
}
