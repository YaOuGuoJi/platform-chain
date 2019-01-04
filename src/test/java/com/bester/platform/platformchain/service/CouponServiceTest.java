package com.bester.platform.platformchain.service;

import com.bester.platform.platformchain.dto.CouponDTO;
import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author liuwen
 * @date 2018/12/29
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CouponServiceTest {

    @Resource
    private CouponService couponService;

    @Test
    public void testBatchSelect() {
        List<Integer> couponIds = Lists.newArrayList(1, 2, 3, 4, 5, 6, 1, 1, 2, 3, 5);
        Map<Integer, CouponDTO> map = couponService.batchFindByCouponIds(couponIds);
        Assert.assertEquals(map.size(), 6);
    }
}
