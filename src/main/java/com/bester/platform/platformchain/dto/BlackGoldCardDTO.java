package com.bester.platform.platformchain.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author liuwen
 * @date 2018/12/11
 */
@Data
public class BlackGoldCardDTO implements Serializable {

    private static final long serialVersionUID = 3199420496089391593L;

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
