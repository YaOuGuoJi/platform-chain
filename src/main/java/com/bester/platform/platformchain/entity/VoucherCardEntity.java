package com.bester.platform.platformchain.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author liuwen
 * @date 2018/12/13
 */
@Data
public class VoucherCardEntity {

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 卡号
     */
    private String cardId;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 绑定用户id
     */
    private Integer userId;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 添加时间
     */
    private Date addTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
