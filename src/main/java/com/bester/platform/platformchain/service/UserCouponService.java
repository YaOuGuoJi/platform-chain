package com.bester.platform.platformchain.service;

import com.bester.platform.platformchain.dto.UserCouponDTO;

import java.util.List;
import java.util.Map;

/**
 * @author zhangqiang
 * @date 2018-12-18
 */

public interface UserCouponService {

    /**
     * 根据用户的id和优惠券id查询该用户领取该类型优惠券的数量
     *
     * @param userId
     * @param couponId
     * @return
     */
    int findCouponCountById(Integer userId, Integer couponId);

    /**
     * 根据优惠劵状态查找用户优惠券
     * @see com.bester.platform.platformchain.constant.Coupon
     *
     * @param userId
     * @param status
     * @return
     */
    List<UserCouponDTO> findUserCouponByStatus(Integer userId, Integer status);


    /**
     * 用户领取优惠券
     *
     * @param userId
     * @param couponId
     * @return
     */
    int receiveCoupon(Integer userId, Integer couponId);

    /**
     * 批量查询优惠券用户领取数量
     *
     * @param userId
     * @param couponIds
     * @return
     */
    Map<Integer, Integer> selectCouponCount(Integer userId, List<Integer> couponIds);
}
