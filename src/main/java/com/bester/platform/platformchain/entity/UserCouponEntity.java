package com.bester.platform.platformchain.entity;

import lombok.Data;

import java.util.Date;

/**
 * 用户领券实体
 *
 * @author zhangqiang
 * @date 2018-12-17
 */
@Data
public class UserCouponEntity {

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
     * 领取时间
     */
    private Date pickUpTime;

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
