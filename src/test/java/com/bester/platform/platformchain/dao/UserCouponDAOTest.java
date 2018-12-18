package com.bester.platform.platformchain.dao;

import com.bester.platform.platformchain.entity.UserCouponEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserCouponDAOTest {
    @Resource
    private UserCouponDao userCouponDao;
    @Test
    public void receiveCoupon(){
        UserCouponEntity userCoupon=new UserCouponEntity();
        userCoupon.setUserId(1);
        userCoupon.setCouponId(2);
        userCoupon.setShopId(3);
        userCoupon.setStatus(2);
        userCoupon.setFailureTime(new Date());
        int i = userCouponDao.receiveCoupon(userCoupon);
        System.out.println(i);
    }
}
