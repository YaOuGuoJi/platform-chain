package com.bester.platform.platformchain.service;

/**
 * @author liuwen
 * @date 2018/12/5
 */
public interface SmsClientService {

    /**
     * 发送短信验证码
     *
     * @param phoneNum
     * @return
     */
    int sendVerifyCode(String phoneNum);

    /**
     * 验证手机验证码
     *
     * @param phoneNum
     * @param code
     * @return
     */
    int verifyCode(String phoneNum, String code);

}
