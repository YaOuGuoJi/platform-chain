package com.bester.platform.platformchain.entity;

import lombok.Data;
/**
 * @author lizhi
 * @date 2018-12-24
 */
@Data
public class PageQueryToolEntity {

    /**
     * 优惠卷ID
     */
    private Integer couponId;
    /**
     * 优惠卷领取个数
     */
    private Integer count;
}
