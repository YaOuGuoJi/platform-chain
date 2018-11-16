package com.bester.platform.platformchain.service;

import com.bester.platform.platformchain.dto.UserInfoDTO;
import com.bester.platform.platformchain.entity.UserInfoEntity;

import java.util.Date;

/**
 * @author liuwen
 * @date 2018/11/12
 */
public interface UserInfoService {

    /**
     * 查询用户详细信息
     *
     * @param userId
     * @return
     */
    UserInfoDTO findUserInfoByUserId(int userId);

    /**
<<<<<<< HEAD
     * 动态添加用户信息
     *
     * @param userInfoEntity
     * @return
     */
    int insertUserInfo(UserInfoEntity userInfoEntity);

=======
     * 修改用户信息
     * @param userId
     * @param birth
     * @param userName
     * @param sex
     * @param phone
     * @param email
     * @param address
     * @param job
     * @return
     */
    int updateUserInfo(int userId, Date birth, String userName, int sex, String phone, String email, String address, String job);
>>>>>>> 20181106ljw
}
