package com.bester.platform.platformchain.dao;

import com.bester.platform.platformchain.entity.BlackGoldCardEntity;
import com.bester.platform.platformchain.util.RandomUtil;
import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Arrays;
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
//        List<String> str = buildCardIdList();
//        List<BlackGoldCardEntity> list = Lists.newArrayList();
//        for (String a : str) {
//            String cardId = "BG0001" + a;
//            BlackGoldCardEntity entity = new BlackGoldCardEntity();
//            entity.setCardId(cardId);
//            entity.setPassword(RandomUtil.getStringRandom(8));
//            list.add(entity);
//        }
//        int i = blackGoldCardDao.addBlackGoldCards(list);
//        System.out.println("添加成功: " + i);
//        Assert.assertEquals(i, 50);

        List<BlackGoldCardEntity> list = Lists.newArrayList();
        for (int i = 0; i < 650; i++) {
            BlackGoldCardEntity entity = new BlackGoldCardEntity();
            entity.setCardId(buildCardId());
            entity.setPassword(RandomUtil.getStringRandom(8));
            list.add(entity);
        }
        int i = blackGoldCardDao.addBlackGoldCards(list);
        System.out.println("添加成功: " + i);
        Assert.assertEquals(i, 650);
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
        String[] arr = new String[]{"888666", "996699", "698896", "111111", "898989",
                "686868", "988989", "899688", "123456", "654321",
                "008666", "889868", "889988", "000001", "000000",
                "868968", "000006", "000009", "000008", "000888",
                "896698", "989999", "989989", "666888", "698696",
                "888888", "999666", "869968", "896668", "000999",
                "969899", "686686", "868686", "886898", "888999",
                "999999", "868868", "006888", "000666", "006688",
                "989898", "666666", "869686", "999888", "889999",
                "666688", "886899", "896996", "886998", "998668"};
        Arrays.sort(arr);
        return Lists.newArrayList(arr);
    }

    @Test
    public void addTestData() {
        List<BlackGoldCardEntity> entities = Lists.newArrayList();
        for (int i = 0; i < 100; i++) {
            String cardID = RandomUtil.justStringRandom(12);
            BlackGoldCardEntity entity = new BlackGoldCardEntity();
            entity.setCardId(cardID);
            entity.setPassword("LIUWEN12");
            entities.add(entity);
        }
        int result = blackGoldCardDao.addBlackGoldCards(entities);
        Assert.assertEquals(result, 100);
    }
}
