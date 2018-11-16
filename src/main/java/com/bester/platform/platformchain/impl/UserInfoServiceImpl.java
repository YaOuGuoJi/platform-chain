package com.bester.platform.platformchain.impl;

import com.bester.platform.platformchain.dao.UserInfoDao;
import com.bester.platform.platformchain.dto.UserInfoDTO;
import com.bester.platform.platformchain.entity.UserInfoEntity;
import com.bester.platform.platformchain.service.UserInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
    public int insertUserInfo(UserInfoEntity userInfoEntity) {
        return userInfoDao.insertUserInfo(userInfoEntity);
    }
}
