package com.bester.platform.platformchain.service;

import com.bester.platform.platformchain.dao.UserInfoDao;
import com.bester.platform.platformchain.entity.UserInfoEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author zhangqiang
 * @date 2018/11/15
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserInfoTest {


    @Resource
    private UserInfoDao userInfoDao;
    @Resource
    private UserInfoService userInfoService;

    @Test
    public void testInsert() {
        UserInfoEntity userInfoEntity = new UserInfoEntity();
        userInfoEntity.setUserName("abcabc");
        int insert = userInfoDao.insertUserInfo(userInfoEntity);
        System.out.println(insert);
    }
}
