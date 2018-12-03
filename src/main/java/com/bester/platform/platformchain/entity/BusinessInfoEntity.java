package com.bester.platform.platformchain.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author yanrui
 */
@Data
public class BusinessInfoEntity {

    private int id;

    private String registerPhone;

    private String name;

    private String phone;

    private String weChat;

    private String introduce;

    private int type;

    private String remarks;

    private Date addTime;

    private Date updateTime;
}
