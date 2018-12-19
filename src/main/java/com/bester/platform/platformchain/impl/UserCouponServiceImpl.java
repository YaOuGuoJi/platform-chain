package com.bester.platform.platformchain.impl;

import com.bester.platform.platformchain.dao.UserCouponDao;
import com.bester.platform.platformchain.dto.UserCouponDTO;
import com.bester.platform.platformchain.entity.UserCouponEntity;
import com.bester.platform.platformchain.service.UserCouponService;
import org.springframework.beans.BeanUtils;
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
    @Override
    public int receiveCoupon(UserCouponDTO userCouponDTO) {
        if(userCouponDTO==null){
            return 0;
        }
        String shopIdString = userCouponDTO.getShopId().toString();
        String shopId = shopIdString.substring(shopIdString.indexOf("[") + 1, shopIdString.indexOf("]"));
        UserCouponEntity userCouponEntity=new UserCouponEntity();
        BeanUtils.copyProperties(userCouponDTO,userCouponEntity);
        userCouponEntity.setShopId(shopId);
        return userCouponDao.receiveCoupon(userCouponEntity);
    }

    @Override
    public int findCouponCountById(Integer userId, Integer couponId) {
        return userCouponDao.findCouponCountById(userId,couponId);
    }
}
