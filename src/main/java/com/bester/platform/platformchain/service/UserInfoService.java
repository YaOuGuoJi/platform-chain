package com.bester.platform.platformchain.service;

import com.bester.platform.platformchain.dto.UserInfoDTO;
import com.bester.platform.platformchain.entity.UserInfoEntity;

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
     * 动态添加用户信息
     *
     * @param userInfoDTO
     * @return
     */
    int insertUserInfo(UserInfoDTO userInfoDTO);

    /**
     * 修改用户信息
     * @param userInfoDTO
     * @return
     */

    int updateUserInfo(UserInfoDTO userInfoDTO);
}
