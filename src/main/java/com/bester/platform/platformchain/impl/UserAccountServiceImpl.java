package com.bester.platform.platformchain.impl;

import com.bester.platform.platformchain.dao.UserAccountDao;
import com.bester.platform.platformchain.dao.UserLoginDao;
import com.bester.platform.platformchain.dto.UserAccountDTO;
import com.bester.platform.platformchain.entity.UserAccountEntity;
import com.bester.platform.platformchain.service.UserAccountService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author liuwen
 * @date 2018/11/6
 */
@Service
public class UserAccountServiceImpl implements UserAccountService {

    @Resource
    private UserAccountDao userAccountDao;
    @Resource
    private UserLoginDao userLoginDao;

    @Override
    public UserAccountDTO findUserAccountInfoByPhoneNum(String phoneNum) {
        UserAccountEntity userAccountInfo = userAccountDao.findUserAccountInfoByPhoneNum(phoneNum);
        if (userAccountInfo == null) {
            return null;
        }
        UserAccountDTO userAccountDTO = new UserAccountDTO();
        BeanUtils.copyProperties(userAccountInfo, userAccountDTO);
        return userAccountDTO;
    }

    @Override
    public int addLoginRecord(int userId) {
        return userLoginDao.insertUserLoginRecord(userId);
    }


    @Override
    public int addUserAccountInfo(UserAccountDTO userAccountDTO) {
        if (userAccountDTO == null) {
            return 0;
        }
        UserAccountEntity userAccountEntity = new UserAccountEntity();
        BeanUtils.copyProperties(userAccountDTO, userAccountEntity);
        userAccountDao.insertUserAccountInfo(userAccountEntity);
        return userAccountEntity.getUserId();
    }

    @Override
    public UserAccountDTO findUserAccountInfoByInviteCode(String inviteCode) {
        UserAccountEntity userAccountEntity = userAccountDao.selectByInviteCode(inviteCode);
        if (userAccountEntity == null) {
            return null;
        }
        UserAccountDTO userAccountDTO = new UserAccountDTO();
        BeanUtils.copyProperties(userAccountEntity, userAccountDTO);
        return userAccountDTO;
    }

    @Override
    public int addUserInviteTimes(int userId) {
        return userAccountDao.addUserInviteTimes(userId);
    }

    @Override
    public UserAccountDTO findUserAccountInfoByUserId(int userId) {
        UserAccountEntity userAccountEntity = userAccountDao.selectByUserId(userId);
        if (userAccountEntity == null) {
            return null;
        }
        UserAccountDTO userAccountDTO = new UserAccountDTO();
        BeanUtils.copyProperties(userAccountEntity, userAccountDTO);
        return userAccountDTO;
    }
}
