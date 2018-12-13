package com.bester.platform.platformchain.dao;

import com.bester.platform.platformchain.entity.BlackGoldCardEntity;
import com.bester.platform.platformchain.util.InviteCodeUtil;
import com.bester.platform.platformchain.util.RandomUtil;
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
        String cardId = buildCardId();
        System.out.println(cardId);

//        List<BlackGoldCardEntity> list = Lists.newArrayList();
//        for (int i = 0; i < 100; i++) {
//            BlackGoldCardEntity entity = new BlackGoldCardEntity();
//            entity.setCardId(buildCardId());
//            entity.setPassword(RandomUtil.getStringRandom(8));
//            list.add(entity);
//        }
//        int i = blackGoldCardDao.addBlackGoldCards(list);
//        System.out.println("添加成功: " + i);
//        Assert.assertEquals(i, 100);
    }

    private String buildCardId() {
        StringBuilder sb = new StringBuilder("BG0001");
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            int num = random.nextInt(10);
            sb.append(String.valueOf(num));
        }
        return sb.toString();
    }

    private List<String> buildCardIdList() {
        return Lists.newArrayList("000000", "111111", "666666", "888888", "999999",
                "000001", "000006", "000008", "000009", "000666", "000888", "666888", "006688", "006888", "888666",
                "123456", "654321", "888999", "999888", "000999", "999666", "686868", "868686", "898989", "989898",
                "008666", "");

    }


}
