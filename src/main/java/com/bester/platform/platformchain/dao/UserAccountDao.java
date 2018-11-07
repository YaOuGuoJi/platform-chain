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
    UserAccountEntity findUserAccountInfo(@Param("userId") int userId);
}
