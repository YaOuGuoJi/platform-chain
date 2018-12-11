package com.bester.platform.platformchain.entity;

import lombok.Data;

import java.util.Date;

@Data
public class BrandInfoEntity {

    private String BrandId;

    private String BrandName;

    private String BrandLogo;

    private int Deleted;

    private String Floor;

    private String Address;

    private String phoneNum;

    private String ShopPicture;

    private Date AddTime;

    private Date UpdateTime;
}
