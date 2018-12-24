package com.bester.platform.platformchain.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author zhangqiang
 * @date 2018-12-17
 */
@Data
public class UserCouponDTO implements Serializable {

    private static final long serialVersionUID = -1492632368646875634L;
    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 商户ID
     */
    private Integer shopId;

    /**
     * 优惠券ID
     */
    private Integer couponId;

    /**
     * 失效时间
     */
    private Date failureTime;

    /**
     * 状态（1：已使用，2：未使用)
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
