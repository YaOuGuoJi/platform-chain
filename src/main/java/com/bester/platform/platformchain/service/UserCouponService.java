package com.bester.platform.platformchain.service;

import com.bester.platform.platformchain.dao.UserCouponDao;
import com.bester.platform.platformchain.dto.UserCouponDTO;

/**
 * @author zhangqiang
 * @date 2018-12-18
 */

public interface UserCouponService {
    /**
     * 用户领取优惠券
     * @param userCouponDTO
     * @return
     */
   int receiveCoupon(UserCouponDTO userCouponDTO);
}
