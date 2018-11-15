package com.bester.platform.platformchain.service;

import com.bester.platform.platformchain.dto.UserAccountDTO;

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
     * 检查用户名和密码匹配性
     *
     * @param userName
     * @param password
     * @return 返回生成token
     */
    boolean checkUserPassword(String userName, String password);

    /**
     * 新增一条登录记录
     *
     * @param userId
     * @return
     */
    int addLoginRecord(int userId);

    /**
     * 查找用户名是否存在
     *
     * @param userName
     * @return
     */
    UserAccountDTO findUserAccountInfoByUserName(String userName);

    /**
     * 添加用户
     *
     * @param userName
     * @param password
     * @return
     */
    int addUserAccountInfo(String userName, String password);

}
