package com.bester.platform.platformchain.dao;

import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * @author zhangqiang
 * @date 2018/11/6
 */
public interface UserLoginDao {

    /**
     * 查找用户上次登录时间
     * @param userId 用户ID
     * @return
     */
    Date findUserLastLoginTime(@Param("userId") Integer userId);

}
