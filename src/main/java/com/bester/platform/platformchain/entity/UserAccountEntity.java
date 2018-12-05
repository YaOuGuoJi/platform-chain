package com.bester.platform.platformchain.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author liuwen
 * @date 2018/11/6
 */
@Data
public class UserAccountEntity {

    /**
     * 用户id
     */
    private Integer userId;


    /**
     * 邀请码
     */
    private String inviteCode;

    /**
     * 邀请次数
     */
    private Integer inviteTimes;

    /**
     * 添加时间
     */
    private Date addTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 手机号
     */
    private String phoneNum;

}
