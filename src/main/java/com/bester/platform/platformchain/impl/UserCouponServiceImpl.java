package com.bester.platform.platformchain.impl;

import com.bester.platform.platformchain.constant.Coupon;
import com.bester.platform.platformchain.dao.CouponDao;
import com.bester.platform.platformchain.dao.UserCouponDao;
import com.bester.platform.platformchain.dto.UserCouponDTO;
import com.bester.platform.platformchain.entity.CountEntity;
import com.bester.platform.platformchain.entity.CouponEntity;
import com.bester.platform.platformchain.entity.UserCouponEntity;
import com.bester.platform.platformchain.service.UserCouponService;
import com.bester.platform.platformchain.util.BeansListUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zhangqiang
 * @date 2018-12-18
 */
@Service
public class UserCouponServiceImpl implements UserCouponService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserCouponServiceImpl.class);
    @Resource
    private CouponDao couponDao;
    @Resource
    private UserCouponDao userCouponDao;

    @Override
    public List<UserCouponDTO> findUnusedAndUsedCoupon(Integer userId, Integer status) {
        List<UserCouponEntity> couponEntities = userCouponDao.findUnusedAndUsedCoupon(userId, status);
        List<UserCouponDTO> userCouponDTOs = BeansListUtils.copyListProperties(couponEntities, UserCouponDTO.class);
        return userCouponDTOs;
    }

    @Override
    public List<UserCouponDTO> findExpiredCoupon(Integer userId) {
        List<UserCouponEntity> expiredCoupon = userCouponDao.findExpiredCoupon(userId);
        List<UserCouponDTO> userCouponDTOs = BeansListUtils.copyListProperties(expiredCoupon, UserCouponDTO.class);
        return userCouponDTOs;
    }

    @Override
    public int receiveCoupon(Integer userId, Integer couponId) {
        Assert.isTrue(userId != null && userId > 0, "userId不合法！");
        Assert.isTrue(couponId != null && couponId > 0, "couponId不合法！");
        CouponEntity couponEntity = couponDao.inquireCouponById(couponId);
        if (couponEntity == null || couponEntity.getMargin() <= 0) {
            return 0;
        }
        DateTime today = new DateTime();
        Date failureTime = today.plusDays(couponEntity.getValidityPeriod()).toDate();
        UserCouponEntity userCouponEntity = new UserCouponEntity();
        userCouponEntity.setUserId(userId);
        userCouponEntity.setCouponId(couponId);
        userCouponEntity.setFailureTime(failureTime);
        userCouponEntity.setStatus(Coupon.UNUSED);
        int couponResult = couponDao.updateCouponNum(couponId);
        if (couponResult <= 0) {
            LOGGER.error("优惠券数量减一失败！couponId: {}", couponId);
            return couponResult;
        }
        return userCouponDao.receiveCoupon(userCouponEntity);
    }

    @Override
    public int findCouponCountById(Integer userId, Integer couponId) {
        return userCouponDao.findCouponCountById(userId, couponId);
    }
    @Override
    public Map<Integer, Integer> selectCouponCount(Integer userId, List<Integer> couponIds) {
        List<CountEntity> entities = userCouponDao.selectCouponCount(userId, couponIds);
        if (CollectionUtils.isEmpty(entities)) {
            return Collections.emptyMap();
        }
        return entities.stream().collect(Collectors.toMap(CountEntity::getId, CountEntity::getResult));
    }
}
