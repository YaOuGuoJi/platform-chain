package com.bester.platform.platformchain.service;

import com.bester.platform.platformchain.dto.UserAccountDTO;

/**
 * @author liuwen
 * @date 2018/11/6
 */
public interface UserAccountService {

    /**
     * 检查用户是否已经注册
     *
     * @param phoneNum
     * @return
     */
    UserAccountDTO findUserAccountInfoByPhoneNum(String phoneNum);

    /**
     * 新增一条登录记录
     *
     * @param userId
     * @return
     */
    int addLoginRecord(int userId);


    /**
     * 添加用户
     *
     * @param userAccountDTO
     * @return
     */
    int addUserAccountInfo(UserAccountDTO userAccountDTO);

    /**
     * 根据用户邀请码查询用户信息
     *
     * @param inviteCode
     * @return
     */
    UserAccountDTO findUserAccountInfoByInviteCode(String inviteCode);

    /**
     * 用户邀请次数+1
     *
     * @param userId
     * @return
     */
    int addUserInviteTimes(int userId);

    /**
     * 根据用户id查询用户信息
     *
     * @param userId
     * @return
     */
    UserAccountDTO findUserAccountInfoByUserId(int userId);

}
