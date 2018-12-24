package com.bester.platform.platformchain.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yanrui
 */
@Data
public class BrandInfoDTO implements Serializable {

    private static final long serialVersionUID = 3853178276569764006L;
    /**
     * 品牌id
     */
    private Integer brandId;

    /**
     *品牌名称
     */
    private String brandName;

    /**
     * 品牌logo
     */
    private String brandLogo;

    /**
     * 是否删除
     */
    private Integer deleted;

    /**
     * 楼号
     */
    private Integer floor;

    /**
     * 具体区位
     */
    private String address;

    /**
     * 联系方式
     */
    private String phoneNum;

    /**
     * 商铺图片
     */
    private String shopPicture;

    /**
     * 品牌介绍
     */
    private String introduce;

    /**
     * 添加时间
     */
    private Date addTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 业态
     */
    private Integer brandType;

    /**
     * 点赞数
     */
    private Integer praiseNum;

    /**
     * 收藏数
     */
    private Integer collectNum;
}
