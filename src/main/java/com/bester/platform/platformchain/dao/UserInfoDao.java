package com.bester.platform.platformchain.dao;

import java.util.List;

/**
 * @author zhangqiang
 */
public interface UserInfoDao {

    /**
     * 查询所有的用户ID
     * @return
     */
    List<Integer> userIdList();

}
