package com.bester.platform.platformchain.impl;

import com.bester.platform.platformchain.dao.UserCouponDao;
import com.bester.platform.platformchain.dto.UserCouponDTO;
import com.bester.platform.platformchain.entity.UserCouponEntity;
import com.bester.platform.platformchain.service.UserCouponService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;

/**
 * @author zhangqiang
 * @date 2018-12-18
 */
@Service
public class UserCouponServiceImpl implements UserCouponService {
    @Resource
    private UserCouponDao userCouponDao;

    @Override
    public int receiveCoupon(UserCouponDTO userCouponDTO) {
        if(userCouponDTO==null){
            return 0;
        }
        UserCouponEntity userCouponEntity=new UserCouponEntity();
        BeanUtils.copyProperties(userCouponDTO,userCouponEntity);
        return userCouponDao.receiveCoupon(userCouponEntity);
    }
}
