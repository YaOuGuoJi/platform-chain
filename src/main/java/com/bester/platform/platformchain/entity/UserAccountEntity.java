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
    private int userId;

    /**
     * 密码
     */
    private String password;

    /**
     * 添加时间
     */
    private Date addTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
