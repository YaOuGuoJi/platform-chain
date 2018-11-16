package com.bester.platform.platformchain.dao;

import com.bester.platform.platformchain.entity.UserInfoEntity;
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

}
