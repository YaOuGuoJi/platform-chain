package com.bester.platform.platformchain.dao;

import com.bester.platform.platformchain.entity.CouponEntity;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

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

    /**
     * 根据ID查询优惠券信息
     *
     * @param couponId
     * @return
     */
    CouponEntity inquireCouponById(@Param("id") Integer couponId);

    /**
     * 批量查询
     *
     * @param couponIds
     * @return
     */
    List<CouponEntity> batchSelect(@Param("couponIds") Collection<Integer> couponIds);

    /**
     * 查询可领取优惠券
     *
     * @return
     */
    List<CouponEntity> queryAllCouponInfo();

    /**
     * 更新优惠券信息
     *
     * @param coupon
     * @return
     */
    int updateCouponInfo(@Param("coupon") CouponEntity coupon);

    /**
     * 更新优惠券的数量
     *
     * @param id
     * @return
     */
    int updateCouponNum(@Param("id") int id);


}
