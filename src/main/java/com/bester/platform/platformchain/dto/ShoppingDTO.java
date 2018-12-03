package com.bester.platform.platformchain.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 购物页商品实体
 *
 * @author zhangqiang
 */
@Data
public class ShoppingDTO implements Serializable {

    private static final long serialVersionUID = -8373330444813905823L;
    /**
     * 商品ID
     */
    private Long commodityId;

    /**
     * 商品名称
     */
    private String commodityName;

    /**
     * 商品主图
     */
    private String mainImageUrl;

    /**
     * 商品详情
     */
    private String commodityDetailUrl;

    /**
     * 淘宝客短连接
     */
    private String shortUrl;

    /**
     * 商品价格
     */
    private BigDecimal price;

    /**
     * 商品销量
     */
    private Integer salesVolume;

}
