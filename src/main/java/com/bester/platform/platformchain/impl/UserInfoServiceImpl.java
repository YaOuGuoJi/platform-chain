package com.bester.platform.platformchain.impl;

import com.bester.platform.platformchain.dao.UserInfoDao;
import com.bester.platform.platformchain.dto.UserInfoDTO;
import com.bester.platform.platformchain.entity.UserInfoEntity;
import com.bester.platform.platformchain.service.UserInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author liuwen
 * @date 2018/11/12
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Resource
    private UserInfoDao userInfoDao;

    @Override
    public UserInfoDTO findUserInfoByUserId(int userId) {
        UserInfoEntity userInfoEntity = userInfoDao.selectById(userId);
        if (userInfoEntity == null) {
            return null;
        }
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        BeanUtils.copyProperties(userInfoEntity, userInfoDTO);
        return userInfoDTO;
    }

    @Override
<<<<<<< HEAD
    public int insertUserInfo(UserInfoEntity userInfoEntity) {
        return userInfoDao.insertUserInfo(userInfoEntity);
=======
    public int updateUserInfo(int userId, Date birth, String userName, int sex, String phone, String email, String address, String job) {
       return  userInfoDao.updateUserInfo(userId,birth,userName,sex,phone,email,address,job);
>>>>>>> 20181106ljw
    }
}
