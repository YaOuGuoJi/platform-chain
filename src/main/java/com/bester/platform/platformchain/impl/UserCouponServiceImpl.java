package com.bester.platform.platformchain.impl;

import com.bester.platform.platformchain.constant.Coupon;
import com.bester.platform.platformchain.dao.CouponDao;
import com.bester.platform.platformchain.dao.UserCouponDao;
import com.bester.platform.platformchain.dto.UserCouponDTO;
import com.bester.platform.platformchain.entity.UserCouponEntity;
import com.bester.platform.platformchain.service.UserCouponService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
    @Resource
    private CouponDao couponDao;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserCouponServiceImpl.class);

    @Override
    public List<Integer> findUnusedAndUsedCouponId(Integer userId, Integer status) {
        return userCouponDao.findUnusedAndUsedCouponId(userId, status);
    }

    @Override
    public List<Integer> findExpiredCoupon(Integer userId) {
        return userCouponDao.findExpiredCoupon(userId);
    }

    @Override
    public int receiveCoupon(UserCouponDTO userCouponDTO, int couponNum) {
        if (userCouponDTO == null) {
            return 0;
        }
        int coupon = couponDao.updateCouponNum(userCouponDTO.getCouponId());
        if (coupon == 0) {
            LOGGER.error("用户" + userCouponDTO.getUserId() + "领取优惠券时，更新优惠券余量失败，此时优惠券剩余" + couponNum + "张");
            return 0;
        }
        String shopId = CollectionUtils.isEmpty(userCouponDTO.getShopId()) ? "" : String.join(",", userCouponDTO.getShopId());
        UserCouponEntity userCouponEntity = new UserCouponEntity();
        BeanUtils.copyProperties(userCouponDTO, userCouponEntity);
        userCouponEntity.setShopId(shopId);
        userCouponEntity.setStatus(Coupon.USED);
        return userCouponDao.receiveCoupon(userCouponEntity);
    }

    @Override
    public int findCouponCountById(Integer userId, Integer couponId) {
        return userCouponDao.findCouponCountById(userId, couponId);
    }
}
