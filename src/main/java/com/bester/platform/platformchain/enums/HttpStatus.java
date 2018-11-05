package com.bester.platform.platformchain.enums;

/**
 * @author liuwen
 * @date 2018/11/3
 */
public enum  HttpStatus {

    /**
     * 成功
     */
    OK(200,"成功"),

    /**
     * 参数错误
     */
    PARAMETER_ERROR(403,"参数错误"),

    /**
     * 找不到资源
     */
    NOT_FOUND(404,"找不到资源"),

    /**
     * 服务端出错
     */
    ERROR(500,"服务端错误");

    public int value;

    public String message;

    HttpStatus(int value, String message) {
        this.value = value;
        this.message = message;
    }
}

