package com.bester.platform.platformchain.impl;

import com.bester.platform.platformchain.dao.UserInfoDao;
import com.bester.platform.platformchain.dto.UserInfoDTO;
import com.bester.platform.platformchain.entity.UserInfoEntity;
import com.bester.platform.platformchain.enums.UserVipLevel;
import com.bester.platform.platformchain.service.UserInfoService;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
        return userInfoDao.updateUserBindPublicNum(userId,bindPublicNum);
    }

    @Override
    public int updateLikeOrCollect(Integer userId, List<String> brandLikeList,List<String> brandCollectList) {
        if (userId == null){
            return 0;
        }
        if (brandLikeList != null && brandCollectList == null) {
            String brandLike = String.join("," , brandLikeList);
            return userInfoDao.updateLikeOrCollect(userId, brandLike,null);
        }else if(brandCollectList != null && brandLikeList == null) {
            String brandCollect = String.join(",", brandCollectList);
            return userInfoDao.updateLikeOrCollect(userId,null, brandCollect);
        }
        return userInfoDao.updateLikeOrCollect(userId,null,null);
    }

    @Override
    public UserInfoDTO selectLikeOrCollect(int userId) {
        if (userId == 0) {
            return null;
        }
        UserInfoEntity userInfoEntity = userInfoDao.selectLikeOrCollect(userId);
        if (userInfoEntity == null) {
            return null;
        }
        String brandLike = userInfoEntity.getBrandLikeList();
        List<String> likeList = Lists.newArrayList(brandLike.split(","));
        String brandCollect = userInfoEntity.getBrandCollectList();
        List<String> collectList = Lists.newArrayList(brandCollect.split(","));
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setBrandLikeList(likeList);
        userInfoDTO.setBrandCollectList(collectList);
        return userInfoDTO;
    }
}
