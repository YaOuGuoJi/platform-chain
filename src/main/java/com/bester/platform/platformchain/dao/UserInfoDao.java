package com.bester.platform.platformchain.dao;

import com.bester.platform.platformchain.entity.UserInfoEntity;
import org.apache.ibatis.annotations.Param;

/**
 * @author zhangqiang
 */
public interface UserInfoDao {

    /**
     * 根据userId查询
     *
     * @param userId
     * @return
     */
    UserInfoEntity selectById(@Param("userId") int userId);

}
