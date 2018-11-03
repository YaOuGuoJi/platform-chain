package com.bester.platform.platformchain.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author liuwen
 * @date 2018/11/2
 */
@Data
public class PowerEntity {

    /**
     * 记录id
     */
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 来源
     */
    private String source;

    /**
     * 算力值
     */
    private Integer power;

    /**
     * 临时算力 1-是 0-不是
     */
    private Integer temporary;

    /**
     * 新增时间
     */
    private Date addTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
