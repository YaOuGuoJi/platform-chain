package com.bester.platform.platformchain.dao;

import com.bester.platform.platformchain.entity.CouponEntity;

import java.util.List;

/**
 * @author zhangqiang
 * @date 2018-12-18
 */

public interface CouponDao {

    /**
     * 查询可领取优惠卷
     *
     * @return
     */
    List<CouponEntity> queryAllCouponInfo();

}
