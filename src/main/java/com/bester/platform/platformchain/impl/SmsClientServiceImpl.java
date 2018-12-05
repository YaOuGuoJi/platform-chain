package com.bester.platform.platformchain.impl;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.bester.platform.platformchain.service.SmsClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author liuwen
 * @date 2018/12/5
 */
public class SmsClientServiceImpl implements SmsClientService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SmsClientServiceImpl.class);

    private static final String VERIFY_CODE_TEMPLATE = "SMS_152415192";

    @Override
    public int sendVerifyCode(String phoneNum) {
        return 0;
    }

    @Override
    public int verifyCode(String phoneNum, String code) {
        return 0;
    }

    private int sendCode(String phoneNum, String temlateCode) {
        final String product = "Dysmsapi";
        final String domain = "dysmsapi.aliyuncs.com";
        final String accessKeyId = "LTAIwk1N65FkASo4";
        final String accessKeySecret = "pUF11Qq9fKPWfeLB5raoagRyKrmrR0";
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);
        SendSmsRequest request = new SendSmsRequest();
        request.setMethod(MethodType.POST);
        request.setPhoneNumbers(phoneNum);
        request.setSignName("亚欧国际小镇");
        request.setTemplateCode("");
        request.setTemplateParam("{\"code\":\"123\"}");
        try {
            SendSmsResponse response = acsClient.getAcsResponse(request);
            if (response.getCode() != null && "OK".equals(response.getCode())) {
                return 1;
            }
            LOGGER.error("发送短信失败, requestId: " + response.getRequestId() + ", code: "
                    + response.getCode() + ", message: " + response.getMessage());
        } catch (ClientException e) {
            LOGGER.error("发送短信错误！", e);
        }
        return 0;
    }

    public static void main(String[] args) throws ClientException {
        //设置超时时间-可自行调整
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        // 初始化ascClient需要的几个参数
        final String product = "Dysmsapi";
        final String domain = "dysmsapi.aliyuncs.com";
        //替换成你的AK
        final String accessKeyId = "LTAIwk1N65FkASo4";
        final String accessKeySecret = "pUF11Qq9fKPWfeLB5raoagRyKrmrR0";
        //初始化ascClient,暂时不支持多region（请勿修改）
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);
        //组装请求对象
        SendSmsRequest request = new SendSmsRequest();
        //使用post提交
        request.setMethod(MethodType.POST);
        //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式；发送国际/港澳台消息时，接收号码格式为国际区号+号码，如“85200000000”
        request.setPhoneNumbers("15991183772");
        //必填:短信签名-可在短信控制台中找到
        request.setSignName("亚欧国际小镇");
        //必填:短信模板-可在短信控制台中找到，发送国际/港澳台消息时，请使用国际/港澳台短信模版
        request.setTemplateCode("SMS_152415189");
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
        request.setTemplateParam("{\"code\":\"123\"}");
        //可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");
        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("yourOutId");
        //请求失败这里会抛ClientException异常
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {

        }
        System.out.println(sendSmsResponse);
    }
}
