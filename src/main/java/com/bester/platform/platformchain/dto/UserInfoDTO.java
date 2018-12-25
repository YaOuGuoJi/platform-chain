package com.bester.platform.platformchain.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author liuwen
 */
@Data
public class UserInfoDTO implements Serializable {

    private static final long serialVersionUID = 1530355721512715892L;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 用户姓名
     */
    private String userName;

    /**
     * 性别 1-男 2-女 其他-未知
     */
    private Integer sex;

    /**
     * 民族
     */
    private String nationality;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 地址
     */
    private String address;

    /**
     * 职业/工作
     */
    private String job;

    /**
     * 汽车id
     */
    private Integer carId;

    /**
     * VIP级别
     * @see com.bester.platform.platformchain.enums.UserVipLevel
     */
    private Integer vip;

    /**
     * 身份证号码
     */
    private String identityId;

    /**
     * 点赞的品牌idList
     */
    private String brandLikeList;

    /**
     * 收藏的品牌idList
     */
    private String brandCollectList;

    /**
     * 添加时间
     */
    private Date addTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否绑定过公众号 1-是 0-否
     */
    private Integer bindPublicNum;
}

