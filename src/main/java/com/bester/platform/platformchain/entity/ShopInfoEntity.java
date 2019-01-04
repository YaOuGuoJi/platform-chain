package com.bester.platform.platformchain.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author liuwen
 * @date 2018/12/29
 */
@Data
public class ShopInfoEntity {

    /**
     * 商户id
     */
    private Integer shopId;

    /**
     * 商户唯一标识
     */
    private String shopUuid;

    /**
     * 商户名称
     */
    private String shopName;

    /**
     * 商户分店名
     */
    private String branchName;

    /**
     * 区域id
     */
    private Integer regionId;

    /**
     * 品牌id
     */
    private Integer brandId;

    /**
     * 商户类型
     */
    private String shopType;

    /**
     * 商户地址
     */
    private String address;

    /**
     * 商户logo
     */
    private String shopLogo;

    /**
     * 店主
     */
    private String shopOwner;

    /**
     * 联系电话
     */
    private String phoneNo;

    /**
     * 联系电话2
     */
    private String phoneNo2;

    /**
     * 人均价格
     */
    private BigDecimal avgPrice;

    /**
     * 删除标记
     */
    private Integer deleted;

    /**
     * 添加时间
     */
    private Date addTime;

    /**
     * 更新时间
     */
    private Date updateTime;

}
