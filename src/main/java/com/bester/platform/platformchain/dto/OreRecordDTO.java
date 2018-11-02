package com.bester.platform.platformchain.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author liuwen
 * @date 2018/11/2
 */
@Data
public class OreRecordDTO implements Serializable {

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
    private Integer source;

    /**
     * 矿值
     */
    private BigDecimal ore;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 添加时间
     */
    private Date addTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
