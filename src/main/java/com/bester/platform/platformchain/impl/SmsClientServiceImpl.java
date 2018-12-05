package com.bester.platform.platformchain.impl;

import com.bester.platform.platformchain.service.SmsClientService;

/**
 * @author liuwen
 * @date 2018/12/5
 */
public class SmsClientServiceImpl implements SmsClientService {

    @Override
    public int sendRegisterMessage(String phoneNum) {
        return 0;
    }

    @Override
    public int sendLoginMessage(String phoneNum) {
        return 0;
    }

    @Override
    public int verifyCode(String phoneNum, String code) {
        return 0;
    }
}
