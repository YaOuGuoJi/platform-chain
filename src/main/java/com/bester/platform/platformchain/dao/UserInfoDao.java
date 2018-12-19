package com.bester.platform.platformchain.dao;

import com.bester.platform.platformchain.entity.UserInfoEntity;
import org.apache.catalina.User;
import org.apache.ibatis.annotations.Param;

/**
 * @author zhangqiang
 */
public interface UserInfoDao {

    /**
     * 动态添加用户信息
     *
     * @param userInfoEntity
     * @return
     */
    int insertUserInfo(@Param("userInfo") UserInfoEntity userInfoEntity);

    /**
     * 根据userId查询
     *
     * @param userId
     * @return
     */
    UserInfoEntity selectById(@Param("userId") int userId);

    /**
     * 用户信息修改
     *
     * @param userInfoEntity
     * @return
     */
    int updateUserInfo(@Param("userInfoEntity") UserInfoEntity userInfoEntity);

    /**
     * 升级会员等级
     *
     * @param userId
     * @param level
     * @return
     */
    int updateUserVipLevel(@Param("userId") int userId, @Param("level") int level);

    /**
     * 根据用户id修改收藏与点赞
     *
     * @param BrandCollectList
     * @param BrandLikeList
     * @return
     */
    int updateLikeOrCollect(@Param("BrandLikeList") String BrandLikeList, @Param("BrandCollectList") String BrandCollectList);

    /**
     * 根据用户id查询用户信息
     *
     * @param UserId
     * @return
     */
    UserInfoEntity selectLikeOrCollect(@Param("UserId") int UserId);

}
