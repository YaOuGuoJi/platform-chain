package com.bester.platform.platformchain.impl;

import com.bester.platform.platformchain.dao.UserInfoDao;
import com.bester.platform.platformchain.dto.UserInfoDTO;
import com.bester.platform.platformchain.entity.UserInfoEntity;
import com.bester.platform.platformchain.enums.UserVipLevel;
import com.bester.platform.platformchain.service.UserInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    public int updateUserVipLevel(int userId, UserVipLevel level) {
        UserInfoEntity userInfoEntity = userInfoDao.selectById(userId);
        if (userInfoEntity == null || userInfoEntity.getVip().equals(level.level)) {
            return 0;
        }
        return userInfoDao.updateUserVipLevel(userId, level.level);
    }

    @Override
    public int updateLikeOrCollect(List<Integer> brandLikeList,List<Integer> brandCollectList) {
        if (brandLikeList != null && brandCollectList == null) {
            List<String> newLikeList = new ArrayList<>(brandLikeList.size());
            for (Integer myInt : brandLikeList) {
                newLikeList.add(String.valueOf(myInt));
            }
            String brandLike = String.join(",", newLikeList);
            return userInfoDao.updateLikeOrCollect(brandLike,null);
        }else if(brandCollectList != null && brandLikeList == null) {
            List<String> newCollectList = new ArrayList<>(brandCollectList.size());
            for (Integer myInt : brandCollectList) {
                newCollectList.add(String.valueOf(myInt));
            }
            String brandCollect = String.join(",", newCollectList);
            return userInfoDao.updateLikeOrCollect(null, brandCollect);
        }
        return userInfoDao.updateLikeOrCollect(null,null);
    }

    @Override
    public UserInfoDTO selectLikeOrCollect(int UserId) {
        if (UserId == 0) {
            return null;
        }
        UserInfoEntity userInfoEntity = userInfoDao.selectLikeOrCollect(UserId);
        String brandLike = userInfoEntity.getBrandLikeList();
        List<String> likeList = Arrays.asList(brandLike.split(","));
        List<Integer> likeIntegerList = new ArrayList<>();
        if (!likeList.isEmpty()) {
            likeIntegerList = likeList.stream().map(Integer::parseInt)
                    .collect(Collectors.toList());
        }
        String brandCollect = userInfoEntity.getBrandCollectList();
        List<String> collectList = Arrays.asList(brandCollect.split(","));
        List<Integer> collectIntegerList = new ArrayList<>();
        if (!collectList.isEmpty()) {
            collectIntegerList = collectList.stream().map(Integer::parseInt)
                    .collect(Collectors.toList());
        }
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setBrandLikeList(likeIntegerList);
        userInfoDTO.setBrandCollectList(collectIntegerList);
        return userInfoDTO;
    }
}
