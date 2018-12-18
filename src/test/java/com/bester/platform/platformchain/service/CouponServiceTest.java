package com.bester.platform.platformchain.service;

import com.bester.platform.platformchain.dto.CouponDTO;
import com.bester.platform.platformchain.enums.UserVipLevel;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangqiang
 * @date 2018-12-18
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class CouponServiceTest {

    @Resource
    private CouponService couponService;

    @Test
    public void testInsert() {
        CouponDTO couponDTO = new CouponDTO();
        couponDTO.setCouponName("满前减百");
        couponDTO.setMargin(10);
        couponDTO.setCouponType(1);
        couponDTO.setOfferCash(new BigDecimal("100.00"));
        couponDTO.setThreshold(new BigDecimal("1000.00"));
        couponDTO.setVipLevel(UserVipLevel.BlackGold.level);
        couponDTO.setLimitNum(1);
        couponDTO.setValidityPeriod(15);
        List<String> list = new ArrayList<>();
        list.add("10010");
        list.add("10086");
        couponDTO.setAvailable(list);
        int result = couponService.addCoupon(couponDTO);
        Assert.assertTrue(result > 0);
    }

}
