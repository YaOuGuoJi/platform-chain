package com.bester.platform.platformchain.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author yanrui
 */
@Data
public class BrandInfoEntity {

    private Integer BrandId;

    private String BrandName;

    private String BrandLogo;

    private Integer Deleted;

    private Integer Floor;

    private String Address;

    private String phoneNum;

    private String ShopPicture;

    private String Introduce;

    private Date AddTime;

    private Date UpdateTime;

    private String type;

    private Integer praiseNum;

    private Integer collectNum;
}
