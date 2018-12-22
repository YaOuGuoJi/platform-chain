package com.bester.platform.platformchain.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author liuwen
 * @date 2018/12/19
 */
@Data
public class UserIdentityDTO implements Serializable {
    private static final long serialVersionUID = -4403151945864889065L;
    /**
     * 姓名
     */
    private String name;
    /**
     * 性别
     */
    private String sex;
    /**
     * 民族
     */
    private String nationality;
    /**
     * 生日
     */
    private String birthday;
    /**
     * 住址
     */
    private String address;
    /**
     * 身份证号码
     */
    private String identityId;
}
