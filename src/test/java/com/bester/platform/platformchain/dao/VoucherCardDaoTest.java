package com.bester.platform.platformchain.dao;

import com.bester.platform.platformchain.entity.VoucherCardEntity;
import com.bester.platform.platformchain.util.RandomUtil;
import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author liuwen
 * @date 2018/12/13
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class VoucherCardDaoTest {

    @Resource
    private VoucherCardDao voucherCardDao;

    @Test
    public void testAdd() {
        List<VoucherCardEntity> entityList = Lists.newArrayList();
        for (int i = 0; i < 1000; i++) {
            String cardId = "VO01" + RandomUtil.getStringRandom(12);
            cardId = cardId.replaceAll("O", "0");
            VoucherCardEntity voucherCardEntity = new VoucherCardEntity();
            voucherCardEntity.setCardId(cardId);
            voucherCardEntity.setAmount(new BigDecimal("1000.00"));
            entityList.add(voucherCardEntity);
        }
        int result = voucherCardDao.addCard(entityList);
        Assert.assertEquals(result, 1000);
    }
}
