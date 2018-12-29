package com.bester.platform.platformchain.service;

import com.bester.platform.platformchain.dto.CouponDTO;
import com.github.pagehelper.PageInfo;

import java.util.Collection;
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
     * 根据CouponId批量查询
     *
     * @param couponIds
     * @return
     */
    Map<Integer, CouponDTO> batchFindByCouponIds(Collection<Integer> couponIds);

    /**
     * 更新优惠券信息
     * @param coupon
     * @return
     */
    int updateCouponInfo(CouponDTO coupon);

    /**
     * 查询所有优惠券
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<CouponDTO> queryAllCouponInfo(int pageNum, int pageSize);

}
