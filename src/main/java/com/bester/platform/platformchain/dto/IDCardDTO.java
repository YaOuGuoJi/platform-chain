package com.bester.platform.platformchain.dto;

import lombok.Data;

/**
 * @author liuwen
 * @date 2018/12/19
 */
@Data
public class IDCardDTO {
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
