package com.bester.platform.platformchain.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lizhi
 * @date 2018/12/24
 */
@Data
public class PageQueryToolDTO implements Serializable {

    private static final long serialVersionUID = -5393223332051877012L;
    /**
     * 优惠卷ID
     */
    private Integer couponId;
    /**
     * 优惠卷领取个数
     */
    private Integer count;
}
