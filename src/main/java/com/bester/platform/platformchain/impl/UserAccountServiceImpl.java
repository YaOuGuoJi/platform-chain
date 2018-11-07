package com.bester.platform.platformchain.impl;

import com.bester.platform.platformchain.dao.UserAccountDao;
import com.bester.platform.platformchain.dao.UserLoginDao;
import com.bester.platform.platformchain.entity.UserAccountEntity;
import com.bester.platform.platformchain.service.UserAccountService;
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
    public boolean checkUserIdExist(int userId) {
        UserAccountEntity userAccountInfo = userAccountDao.findUserAccountInfo(userId);
        return userAccountInfo != null;
    }

    @Override
    public boolean checkUserPassword(int userId, String password) {
        UserAccountEntity userAccountInfo = userAccountDao.findUserAccountInfo(userId);
        return password.equals(userAccountInfo.getPassword());
    }

    @Override
    public int addLoginRecord(int userId) {
        return userLoginDao.insertUserLoginRecord(userId);
    }
}
