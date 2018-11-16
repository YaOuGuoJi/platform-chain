package com.bester.platform.platformchain.service;

<<<<<<< HEAD
import com.bester.platform.platformchain.dao.UserInfoDao;
import com.bester.platform.platformchain.entity.UserInfoEntity;
=======
import org.joda.time.DateTime;
>>>>>>> 20181106ljw
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
<<<<<<< HEAD

/**
 * @author zhangqiang
 * @date 2018/11/15
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserInfoTest {


    @Resource
    private UserInfoDao userInfoDao;

    @Test
    public void testInsert() {
        UserInfoEntity userInfoEntity = new UserInfoEntity();
        userInfoEntity.setUserName("abcabc");
        int insert = userInfoDao.insertUserInfo(userInfoEntity);
        System.out.println(insert);
    }

=======
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
>>>>>>> 20181106ljw
}
