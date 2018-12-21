package com.bester.platform.platformchain.dao;

import com.bester.platform.platformchain.entity.CouponEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author zhangqiang
 * @date 2018-12-21
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CouponDaoTest {

    @Resource
    private CouponDao couponDao;

    @Test
    public void selectCouponTest() {
        CouponEntity couponEntity = couponDao.inquireCouponById(2);
        System.out.println(couponEntity.getShopId());
    }
    @Test
    public void updateCouponInfoTest(){
        CouponEntity couponEntity=new CouponEntity();
        couponEntity.setId(2);
        couponEntity.setMargin(9);
        couponDao.updateCouponInfo(couponEntity);
    }

}
