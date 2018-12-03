package com.bester.platform.platformchain.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yanrui
 */
@Data
public class BusinessInfoDTO implements Serializable {

    private static final long serialVersionUID = 8961943766001020899L;

    private Integer id;

    private String registerPhone;

    private String name;

    private String phone;

    private String weChat;

    private String introduce;

    private Integer type;

    private String remarks;

    private Date addTime;

    private Date updateTime;

}
