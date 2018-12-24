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
     * 绑定身份证信息
     *
     * @param userInfoEntity
     * @return
     */
    int bindIdentityInfo(@Param("userInfoEntity") UserInfoEntity userInfoEntity);

    /**
     * 升级会员等级
     *
     * @param userId
     * @param level
     * @return
     */
    int updateUserVipLevel(@Param("userId") int userId, @Param("level") int level);

    /**
     * 更新绑定公众号状态
     * @param userId
     * @param bindPublicNum
     * @return
     */
    int updateUserBindPublicNum(@Param("userId") int userId,@Param("bindPublicNum") int bindPublicNum);

    /**
     * 根据用户id修改收藏与点赞
     *
     * @param userId
     * @param brandCollectList
     * @param brandLikeList
     * @return
     */
    int updateLikeOrCollect(@Param("userId") Integer userId,
                            @Param("brandLikeList") String brandLikeList,
                            @Param("brandCollectList") String brandCollectList);

    /**
     * 查找用户点赞和收藏列表
     *
     * @param userId
     * @return
     */
    UserInfoEntity selectLikeOrCollect(@Param("userId") int userId);

}
