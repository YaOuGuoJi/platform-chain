package com.bester.platform.platformchain.impl;

import com.bester.platform.platformchain.constant.BrandActionType;
import com.bester.platform.platformchain.dao.UserInfoDao;
import com.bester.platform.platformchain.dto.UserInfoDTO;
import com.bester.platform.platformchain.entity.UserInfoEntity;
import com.bester.platform.platformchain.enums.UserVipLevel;
import com.bester.platform.platformchain.service.UserInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
    public int insertUserInfo(UserInfoDTO userInfoDTO) {
        UserInfoEntity userInfoEntity = new UserInfoEntity();
        BeanUtils.copyProperties(userInfoDTO, userInfoEntity);
        return userInfoDao.insertUserInfo(userInfoEntity);
    }

    @Override
    public int updateUserInfo(UserInfoDTO userInfoDTO) {
        UserInfoEntity userInfoEntity = new UserInfoEntity();
        BeanUtils.copyProperties(userInfoDTO, userInfoEntity);
        return userInfoDao.updateUserInfo(userInfoEntity);
    }

    @Override
    public int bindIdentityInfo(UserInfoDTO userInfoDTO) {
        UserInfoEntity userInfoEntity = new UserInfoEntity();
        BeanUtils.copyProperties(userInfoDTO, userInfoEntity);
        return userInfoDao.bindIdentityInfo(userInfoEntity);
    }

    @Override
    public int updateUserVipLevel(int userId, UserVipLevel level) {
        UserInfoEntity userInfoEntity = userInfoDao.selectById(userId);
        if (userInfoEntity == null || userInfoEntity.getVip().equals(level.level)) {
            return 0;
        }
        return userInfoDao.updateUserVipLevel(userId, level.level);
    }

    @Override
    public int updateUserBindPublicNum(int userId, int bindPublicNum) {
        return userInfoDao.updateUserBindPublicNum(userId, bindPublicNum);
    }

    @Override
    public int updateLikeOrCollect(Integer userId, int type, String brandIds) {
        if (userId == null || type <= 0) {
            return 0;
        }
        if (type == BrandActionType.PRAISE) {
            userInfoDao.updateBrandLikes(userId, StringUtils.isEmpty(brandIds) ? "" : brandIds);
        } else if (type == BrandActionType.COLLECT) {
            userInfoDao.updateBrandCollects(userId, StringUtils.isEmpty(brandIds) ? "" : brandIds);
        }
        return 0;
    }
}
