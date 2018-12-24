package com.bester.platform.platformchain.service;

import com.bester.platform.platformchain.dto.CouponDTO;
import com.github.pagehelper.PageInfo;

import java.util.Map;

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

    /**
     * 根据ID查询优惠券信息
     *
     * @param couponId
     * @return
     */
    CouponDTO inquireCouponById(Integer couponId);

    /**
     * 更新优惠券信息
     * @param coupon
     * @return
     */
    int updateCouponInfo(CouponDTO coupon);

    /**
     * 查询所有优惠卷
     * @param id
     * @param pageNum
     * @param pageSize
     * @return
     */
    Map queryAllCouponInfo(int id, int pageNum, int pageSize);

}
