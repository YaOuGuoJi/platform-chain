package com.bester.platform.platformchain.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserCouponServiceTest {

    @Resource
    private UserCouponService userCouponService;

    @Test
    public void receiveCoupon() {
        int i = userCouponService.receiveCoupon(100002798, 6);
        Assert.assertEquals(i, 1);
    }


    @Test
    public void findCouponCountByIdTest(){
        System.out.println(userCouponService.findCouponCountById(3,2));
    }
}
