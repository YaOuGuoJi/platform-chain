package com.bester.platform.platformchain.dao;

import com.bester.platform.platformchain.entity.UserAccountEntity;
import org.apache.ibatis.annotations.Param;

/**
 * @author liuwen
 * @date 2018/11/6
 */
public interface UserAccountDao {

    /**
     * 根据手机号查询用户账号信息
     *
     * @param phoneNum
     * @return
     */
    UserAccountEntity findUserAccountInfoByPhoneNum(@Param("phoneNum") String phoneNum);

    /**
     * 根据用户名查询用户信息
     *
     * @param userName
     * @return
     */
    UserAccountEntity findUserAccountInfoByUserName(@Param("userName") String userName);

    /**
     * 添加用户
     * @param userAccountEntity 用户实体
     * @return
     */
    int insertUserAccountInfo(@Param("userAccountEntity") UserAccountEntity userAccountEntity);

    /**
     * 根据邀请码查询
     *
     * @param inviteCode
     * @return
     */
    UserAccountEntity selectByInviteCode(@Param("inviteCode") String inviteCode);

    /**
     * 用户邀请次数+1
     *
     * @param userId
     * @return
     */
    int addUserInviteTimes(@Param("userId") int userId);

    /**
     * 根据用户id查询
     *
     * @param userId
     * @return
     */
    UserAccountEntity selectByUserId(@Param("userId") int userId);

}
