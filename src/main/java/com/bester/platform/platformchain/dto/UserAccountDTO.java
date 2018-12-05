package com.bester.platform.platformchain.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zhangqiang
 * @date 2018/11/9
 */
@Data
public class UserAccountDTO implements Serializable {

    private static final long serialVersionUID = 8278062878449423764L;

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
