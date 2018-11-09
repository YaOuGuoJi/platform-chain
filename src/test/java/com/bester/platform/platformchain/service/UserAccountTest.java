package com.bester.platform.platformchain.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @Auther: zhangqiang
 * @Date: 2018/11/9
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserAccountTest {

    @Resource
    private UserAccountService userAccountService;

    @Test
    public void testInsert() {
        int insert = userAccountService.addUserAccountInfo("aaa", "aaaccc");
        Assert.assertTrue(insert > 0);
    }

    @Test
    public void testIsUserNameExist() {
        Assert.assertNotNull(userAccountService.findUserAccountInfoByUserName("aaa"));
    }

}
