package com.bester.platform.platformchain.dao;

import com.bester.platform.platformchain.entity.UserAccountEntity;
import org.apache.ibatis.annotations.Param;

/**
 * @author liuwen
 * @date 2018/11/6
 */
public interface UserAccountDao {

    /**
     * 查询用户账号信息
     *
     * @param userId
     * @return
     */
    UserAccountEntity findUserAccountInfoByUserId(@Param("userId") int userId);

    /**
     * 根据用户名查询用户信息
     *
     * @param userName
     * @return
     */
    UserAccountEntity findUserAccountInfoByUserName(@Param("userName") String userName);

    /**
     * 添加用户
     * @param userName
     * @param password
     * @return
     */
    int insertUserAccountInfo(@Param("userName") String  userName, @Param("password") String password);

}
