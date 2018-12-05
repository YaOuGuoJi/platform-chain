package com.bester.platform.platformchain.impl;

import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.bester.platform.platformchain.constant.RedisKeys;
import com.bester.platform.platformchain.service.RedisClientService;
import com.bester.platform.platformchain.service.SmsClientService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Random;

/**
 * @author liuwen
 * @date 2018/12/5
 */
@Service
public class SmsClientServiceImpl implements SmsClientService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SmsClientServiceImpl.class);
    private static final String OK = "OK";
    private static final String VERIFY_CODE_TEMPLATE = "SMS_152286822";
    private static final Long EXPIRED_TIME = 15 * 60 * 1000L;

    @Resource
    private RedisClientService redisClientService;
    @Resource
    private IAcsClient acsClient;

    @Override
    public int sendVerifyCode(String phoneNum) {
        String verifyCode = buildCode();
        SendSmsRequest request = buildRequest(phoneNum, verifyCode);
        try {
            SendSmsResponse response = acsClient.getAcsResponse(request);
            if (response.getCode() != null && OK.equals(response.getCode())) {
                redisClientService.set(RedisKeys.PHONE_VERIFY_CODE + phoneNum, verifyCode, EXPIRED_TIME);
                return 1;
            }
            LOGGER.error("发送短信失败, requestId: " + response.getRequestId() + ", code: " + response.getCode()
                    + ", message: " + response.getMessage());
        } catch (ClientException e) {
            LOGGER.error("调用阿里云发短信接口错误！", e);
        }
        return 0;
    }

    @Override
    public int verifyCode(String phoneNum, String code) {
        String verifyCode = (String) redisClientService.get(RedisKeys.PHONE_VERIFY_CODE + phoneNum);
        if (StringUtils.isEmpty(verifyCode) || !verifyCode.equals(code)) {
            return 0;
        }
        redisClientService.remove(RedisKeys.PHONE_VERIFY_CODE + phoneNum);
        return 1;
    }

    private static String buildCode() {
        final int length = 6;
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    private SendSmsRequest buildRequest(String phoneNum, String code) {
        String json = "{\"code\":\"" + code + "\"}";
        SendSmsRequest request = new SendSmsRequest();
        request.setMethod(MethodType.POST);
        request.setPhoneNumbers(phoneNum);
        request.setSignName("亚欧国际小镇");
        request.setTemplateCode(VERIFY_CODE_TEMPLATE);
        request.setTemplateParam(json);
        return request;
    }
}
