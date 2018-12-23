package com.bester.platform.platformchain.service;

import com.bester.platform.platformchain.dto.UserInfoDTO;
import com.bester.platform.platformchain.enums.UserVipLevel;

import java.util.List;

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
     *
     * @param userInfoDTO
     * @return
     */

    int updateUserInfo(UserInfoDTO userInfoDTO);

    /**
     * 绑定身份证
     *
     * @param userInfoDTO
     * @return
     */
    int bindIdentityInfo(UserInfoDTO userInfoDTO);

    /**
     * 升级用户VIP等级
     *
     * @param userId
     * @param level
     * @return
     */
    int updateUserVipLevel(int userId, UserVipLevel level);

    /**
     * 更新绑定公众号状态
     * @param userId
     * @param bindPublicNum
     * @return
     */
    int updateUserBindPublicNum(int userId, int bindPublicNum);

    /**
     * 修改用户的点赞与收藏品牌
     *
     * @param userId
     * @param brandCollectList
     * @param brandLikeList
     * @return
     */
    int updateLikeOrCollect(Integer userId, List<String> brandLikeList, List<String> brandCollectList);

    /**
     * 根据用户id查询用户信息
     *
     * @param userId
     * @return
     */
    UserInfoDTO selectLikeOrCollect(int userId);
}
