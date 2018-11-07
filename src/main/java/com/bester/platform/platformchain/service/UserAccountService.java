package com.bester.platform.platformchain.service;

/**
 * @author liuwen
 * @date 2018/11/6
 */
public interface UserAccountService {

    /**
     * 检查用户id是否存在
     *
     * @param userId
     * @return
     */
    boolean checkUserIdExist(int userId);

    /**
     * 检查用户Id和密码匹配性
     *
     * @param userId
     * @param password
     * @return 返回生成token
     */
    boolean checkUserPassword(int userId, String password);

    /**
     * 新增一条登录记录
     *
     * @param userId
     * @return
     */
    int addLoginRecord(int userId);

}
