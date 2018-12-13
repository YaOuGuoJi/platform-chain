package com.bester.platform.platformchain.dao;

import com.bester.platform.platformchain.entity.BlackGoldCardEntity;
import com.bester.platform.platformchain.util.InviteCodeUtil;
import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.Random;

/**
 * @author liuwen
 * @date 2018/12/11
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BlackGoldCardDaoTest {

    @Resource
    private BlackGoldCardDao blackGoldCardDao;

    @Test
    public void testAdd() {
        List<BlackGoldCardEntity> list = Lists.newArrayList();
        for (int i = 0; i < 100; i++) {
            BlackGoldCardEntity entity = new BlackGoldCardEntity();
            entity.setCardId(buildCardId());
            entity.setPassword(password());
            list.add(entity);
        }
        int i = blackGoldCardDao.addBlackGoldCards(list);
        System.out.println("添加成功: " + i);
        Assert.assertEquals(i, 100);
    }

    private String buildCardId() {
        StringBuilder sb = new StringBuilder("BG201801");
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            int num = random.nextInt(10);
            sb.append(String.valueOf(num));
        }
        return sb.toString();
    }

    private String password() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            char c = (char) (Math.random() * 26 + 'A');
            sb.append(c);
        }
        return sb.toString();
    }
}
