package com.bester.platform.platformchain.service;

import com.bester.platform.platformchain.dto.BlackGoldCardDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author liuwen
 * @date 2018/12/11
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BlackGoldCardServiceTest {

    @Resource
    private BlackGoldCardService blackGoldCardService;

    @Test
    public void testSelect() {
        BlackGoldCardDTO dto = blackGoldCardService.findBlackGoldCardByCardId("BG2786575296555814");
        Assert.assertEquals(dto.getPassword(), "EMYAHC");
    }

    @Test
    public void testBind() {
        int bindResult = blackGoldCardService.bindCardToUser("BG2786575296555814", 100002767);
        Assert.assertEquals(bindResult, 1);
    }
}
