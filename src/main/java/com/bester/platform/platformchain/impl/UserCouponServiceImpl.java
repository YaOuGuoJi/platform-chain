package com.bester.platform.platformchain.impl;

import com.bester.platform.platformchain.dao.UserCouponDao;
import com.bester.platform.platformchain.service.UserCouponService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhangqiang
 * @date 2018-12-18
 */
@Service
public class UserCouponServiceImpl implements UserCouponService {

    @Resource
    private UserCouponDao userCouponDao;

    @Override
    public List<Integer> findUnusedAndUsedCouponId(Integer userId, Integer status) {
        return userCouponDao.findUnusedAndUsedCouponId(userId, status);
    }

    @Override
    public List<Integer> findExpiredCoupon(Integer userId) {
        return userCouponDao.findExpiredCoupon(userId);
    }
}
