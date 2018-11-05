package com.bester.platform.platformchain.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author liuwen
 * @date 2018/11/3
 */
@Data
public class PowerRecordDTO implements Serializable {
    private static final long serialVersionUID = -6363543095446532552L;

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
     * 是否生效
     */
    private Integer valid;

    /**
     * 添加时间
     */
    private Date addTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
