package com.bester.platform.platformchain.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yanrui
 */
@Data
public class BrandInfoDTO implements Serializable {

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
