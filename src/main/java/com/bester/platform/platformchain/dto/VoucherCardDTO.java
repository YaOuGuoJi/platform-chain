package com.bester.platform.platformchain.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author liuwen
 * @date 2018/12/13
 */
@Data
public class VoucherCardDTO implements Serializable {

    private static final long serialVersionUID = 8455543576427268148L;

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
     * 添加时间
     */
    private Date addTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
