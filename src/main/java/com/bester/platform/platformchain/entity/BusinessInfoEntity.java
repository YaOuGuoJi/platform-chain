package com.bester.platform.platformchain.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author yanrui
 */
@Data
public class BusinessInfoEntity {

    private Integer id;

    private String registerPhone;

    private String businessName;

    private String phone;

    private String weChat;

    private String introduce;

    private Integer businessType;

    private String remarks;

    private Date addTime;

    private Date updateTime;
}
