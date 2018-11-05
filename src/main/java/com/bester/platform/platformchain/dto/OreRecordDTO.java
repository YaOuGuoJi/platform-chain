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

    private static final long serialVersionUID = 8961943766001020899L;
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
