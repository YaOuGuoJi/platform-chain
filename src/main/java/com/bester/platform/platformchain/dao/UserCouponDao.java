package com.bester.platform.platformchain.dao;

import com.bester.platform.platformchain.entity.CountEntity;
import com.bester.platform.platformchain.entity.UserCouponEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @author zhangqiang
 * @date 2018-12-18
 */

public interface UserCouponDao {

    /**
     * 根据用户的id和优惠券id查询该用户领取该类型优惠券的数量
     *
     * @param userId
     * @param couponId
     * @return
     */
    int findCouponCountById(@Param("userId") Integer userId,
                            @Param("couponId") Integer couponId);

    /**
     * 查找用户已过期的优惠券ID
     *
     * @param userId
     * @return
     */
    List<UserCouponEntity> findExpiredCoupon(@Param("userId") Integer userId);

    /**
     * 查找用户已使用和未使用的优惠券ID
     *
     * @param userId
     * @param status
     * @return
     */
    List<UserCouponEntity> findUnusedAndUsedCoupon(@Param("userId") Integer userId,
                                                   @Param("status") Integer status);

    /**
     * 用户领取优惠券
     *
     * @param userCoupon
     * @return
     */
    int receiveCoupon(@Param("userCoupon") UserCouponEntity userCoupon);

    /**
     * 按分页结果查询不可领取优惠卷
     *
     * @param userId
     * @param couponIds
     * @return
     */
    List<CountEntity> selectCouponCount(@Param("userId") Integer userId, @Param("couponIds") List<Integer> couponIds);
}
