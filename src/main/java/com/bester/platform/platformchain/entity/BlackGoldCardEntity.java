package com.bester.platform.platformchain.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author liuwen
 * @date 2018/12/11
 */
@Data
public class BlackGoldCardEntity {

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 卡号
     */
    private String cardId;

    /**
     * 密码
     */
    private String password;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 绑定用户id
     */
    private Integer userId;

    /**
     * 添加时间
     */
    private Date addTime;

    /**
     * 更新时间
     */
    private Date updateTime;

}
