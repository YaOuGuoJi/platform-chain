package com.bester.platform.platformchain.service;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserInfoTest {
    @Resource
    private UserInfoService userInfoService;
    @Test
    public void insertInfoTest(){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date birth = sdf.parse("1994-03-21");
            int result =  userInfoService.updateUserInfo(100001,birth,"123",1,"110","110@qq.com","火星","码农");
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
