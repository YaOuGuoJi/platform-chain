package com.bester.platform.platformchain.service;

import com.bester.platform.platformchain.dto.UserCouponDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserCouponServiceTest {
    @Resource
    private UserCouponService userCouponService;
    @Test
    public void receiveCoupon() throws ParseException {
        SimpleDateFormat s1=new SimpleDateFormat("yyyy-MM-dd");
        UserCouponDTO userCouponDTO=new UserCouponDTO();
        List<String>shopId= new ArrayList<>();
        shopId.add("3");
        shopId.add("2");
        userCouponDTO.setShopId(shopId);
        userCouponDTO.setCouponId(2);
        userCouponDTO.setUserId(3);
        userCouponDTO.setFailureTime(new Date());
        userCouponDTO.setStatus(2);
        String format = s1.format(new Date());
        Date parse = s1.parse(format);
        userCouponDTO.setFailureTime(parse);
//        int i = userCouponService.receiveCoupon(userCouponDTO);
//        System.out.println(i);



    }
    @Test
    public void findCouponCountByIdTest(){
        System.out.println(userCouponService.findCouponCountById(3,2));
    }
}
