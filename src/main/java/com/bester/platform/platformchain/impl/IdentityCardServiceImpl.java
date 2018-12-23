package com.bester.platform.platformchain.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bester.platform.platformchain.constant.RedisKeys;
import com.bester.platform.platformchain.dto.IDCardDTO;
import com.bester.platform.platformchain.service.IdentityCardService;
import com.bester.platform.platformchain.service.RedisClientService;
import com.bester.platform.platformchain.util.auth.Base64Util;
import com.bester.platform.platformchain.util.auth.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * @author liuwen
 * @date 2018/12/18
 */
@Service
public class IdentityCardServiceImpl implements IdentityCardService {

    private static final Logger LOGGER = LoggerFactory.getLogger(IdentityCardServiceImpl.class);

    @Resource
    private RedisClientService redisClientService;

    @Override
    public IDCardDTO idCardOCR(InputStream inputStream) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            BufferedInputStream in = new BufferedInputStream(inputStream);
            short bufSize = 1024;
            byte[] buffer = new byte[bufSize];
            int len1;
            while (-1 != (len1 = in.read(buffer, 0, bufSize))) {
                bos.write(buffer, 0, len1);
            }
            byte[] bytes = bos.toByteArray();
            return idCardOCR(bytes);
        } catch (Exception e) {
            LOGGER.info("身份证识别失败！", e);
        }
        return null;
    }

    @Override
    public IDCardDTO idCardOCR(byte[] imgData) {
        String idCardURL = "https://aip.baidubce.com/rest/2.0/ocr/v1/idcard";
        try {
            String imgStr = Base64Util.encode(imgData);
            // 识别身份证正面id_card_side=front;识别身份证背面id_card_side=back;
            String params = "id_card_side=front&" + URLEncoder.encode("image", "UTF-8") + "="
                    + URLEncoder.encode(imgStr, "UTF-8");
            String accessToken = getAccessToken();
            String result = HttpUtil.post(idCardURL, accessToken, params);
            return parseIDCardDTO(result);
        } catch (Exception e) {
            LOGGER.info("身份证识别失败！", e);
        }
        return null;
    }

    private String getAccessToken() throws Exception {
        String accessToken = (String) redisClientService.get(RedisKeys.BAI_DU_TOKEN);
        if (StringUtils.isEmpty(accessToken)) {
            accessToken = getAccessTokenFromBaiDuCloud();
            redisClientService.set(RedisKeys.BAI_DU_TOKEN, accessToken, 30 * 24 * 3600L);
        }
        return accessToken;
    }

    private String getAccessTokenFromBaiDuCloud() throws Exception {
        // 官网获取的 API Key
        String ak = "KeiCbB9PhLQveF01oOLRr31i";
        // 官网获取的 Secret Key
        String sk = "cUocKMT1ClVOPO6xkdghcAM75mU3ME82";
        // 获取token地址
        String authHost = "https://aip.baidubce.com/oauth/2.0/token?";
        String getAccessTokenUrl = authHost
                // grant_type为固定参数
                + "grant_type=client_credentials"
                + "&client_id=" + ak
                + "&client_secret=" + sk;
        URL realUrl = new URL(getAccessTokenUrl);
        HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
        connection.setRequestMethod("POST");
        connection.connect();
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            result.append(line);
        }
        JSONObject jsonObject = JSON.parseObject(result.toString());
        return jsonObject.getString("access_token");
    }

    private IDCardDTO parseIDCardDTO(String result) {
        if (StringUtils.isNotEmpty(result)) {
            JSONObject jsonObject = JSON.parseObject(result);
            JSONObject wordsResult = jsonObject.getJSONObject("words_result");
            if (wordsResult != null) {
                IDCardDTO idCardDTO = new IDCardDTO();
                idCardDTO.setName(getField("姓名", wordsResult));
                idCardDTO.setSex(getField("性别", wordsResult));
                idCardDTO.setNationality(getField("民族", wordsResult));
                idCardDTO.setBirthday(getField("出生", wordsResult));
                idCardDTO.setAddress(getField("住址", wordsResult));
                idCardDTO.setIdentityId(getField("公民身份号码", wordsResult));
                return idCardDTO;
            }
        }
        return null;
    }

    private String getField(String jsonKey, JSONObject wordsResult) {
        JSONObject field = wordsResult.getJSONObject(jsonKey);
        if (field != null) {
            return field.getString("words");
        }
        return null;
    }
}
