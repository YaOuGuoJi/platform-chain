package com.bester.platform.platformchain.service;

import com.bester.platform.platformchain.dto.UserCouponDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserCouponServiceTest {
    @Resource
    private UserCouponService userCouponService;
    @Test
    public void receiveCoupon(){
        UserCouponDTO userCouponDTO=new UserCouponDTO();
        userCouponDTO.setShopId(1);
        userCouponDTO.setCouponId(2);
        userCouponDTO.setUserId(3);
        userCouponDTO.setFailureTime(new Date());
        userCouponDTO.setStatus(2);
        int i = userCouponService.receiveCoupon(userCouponDTO);
        System.out.println(i);
    }
}
