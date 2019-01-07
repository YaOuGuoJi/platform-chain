package com.bester.platform.platformchain.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bester.platform.platformchain.common.CommonResult;
import com.bester.platform.platformchain.constant.RedisKeys;
import com.bester.platform.platformchain.enums.HttpStatus;
import com.bester.platform.platformchain.service.RedisClientService;
import com.google.common.collect.Maps;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Map;

/**
 * @author zhangqiang
 * @date 2019-01-02
 */

@RestController
public class WeChatController {

    private static final String APP_ID = "wxc7ae94c9cbefa2a3";
    private static final String APP_SECRET = "5b28674ee794263abce83da7a67ed875";
    private static final String TOKEN = "bester";
    private static final String SCOPE = "snsapi_userinfo";

    @Resource
    private RedisClientService redisClientService;

    /**
     * 重定向至微信授权页面
     *
     * @return
     * @throws UnsupportedEncodingException
     */
    @GetMapping("/wechat/authorization")
    public String  getCode() throws UnsupportedEncodingException {
        String redirectUrl = "http://wechat.xianbester.com/home";
        return "https://open.weixin.qq.com/connect/oauth2/authorize?" +
                "appid=" + APP_ID +
                "&redirect_uri=" + URLEncoder.encode(redirectUrl, "UTF-8") +
                "&response_type=code" +
                "&scope=" + SCOPE +
                "&state=STATE#wechat_redirect";
    }

    /**
     * 用户授权之后添加用户信息
     *
     * @param code
     * @return
     */
    @GetMapping("/wechat/addUserInfo")
    public CommonResult addUserInfo(String code) {
        if (StringUtils.isEmpty(code)) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        String accessToken = (String) redisClientService.get(RedisKeys.WECHAT_ACCESS_TOKEN);
        String openId = (String) redisClientService.get(RedisKeys.WECHAT_OPENID + code + "_");
        boolean valid = judgingAccessToken(accessToken, openId, code);
        if (!valid) {
            refreshAccessToken(code);
            accessToken = (String) redisClientService.get(RedisKeys.WECHAT_ACCESS_TOKEN);
            openId = (String) redisClientService.get(RedisKeys.WECHAT_OPENID + code + "_");
        }
        JSONObject userInfoJson = getUserInfoJson(accessToken, openId);
        if (userInfoJson == null) {
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        String nickname = new String(userInfoJson.getString("nickname").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        String sex = userInfoJson.getString("sex");
        String headImgUrl = userInfoJson.getString("headimgurl");
        redisClientService.set("nickname_" + code + "_", nickname);
        redisClientService.set("sex_" + code + "_", sex);
        redisClientService.set("headimgurl_" + code + "_", headImgUrl);
        Map<String, String> map = Maps.newHashMap();
        map.put("code", code);
        return CommonResult.success(map);
    }

    /**
     * 获取微信用户信息
     *
     * @param code
     * @return
     */
    @GetMapping("/wechat/userInfo")
    public CommonResult userInfo(String code) {
        if (StringUtils.isEmpty(code)) {
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        String nickname = (String) redisClientService.get("nickname_" + code + "_");
        String sex = (String) redisClientService.get("sex_" + code + "_");
        String headImgUrl = (String) redisClientService.get("headimgurl_" + code + "_");
        UserInfo userInfo = new UserInfo();
        userInfo.setNickname(nickname);
        userInfo.setSex(sex);
        userInfo.setHeadImgUrl(headImgUrl);
        Map<String, Object> map = Maps.newHashMap();
        map.put("userInfo", userInfo);
        return CommonResult.success(map);
    }

    /**
     * 获得用户信息
     *
     * @param accessToken
     * @param openId
     * @return
     */
    private JSONObject getUserInfoJson(String accessToken, String openId) {
        String getUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo?" +
                "access_token=" + accessToken +
                "&openid=" + openId +
                "&lang=zh_CN";
        return getJsonObject(getUserInfoUrl);
    }

    /**
     * 获得access_token和openid
     *
     * @param code
     * @return
     */
    private void refreshAccessToken(String code) {
        String getAccessTokenAndOpenIdUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?" +
                "appid=" + APP_ID +
                "&secret=" + APP_SECRET +
                "&code=" + code +
                "&grant_type=authorization_code";
        JSONObject jsonObject = getJsonObject(getAccessTokenAndOpenIdUrl);
        if (jsonObject == null) {
            return;
        }
        String accessToken = jsonObject.getString("access_token");
        String openId = jsonObject.getString("openid");
        String expireIn = jsonObject.getString("expires_in");
        Long expireTime = Long.valueOf(expireIn) * 1000L;
        redisClientService.set(RedisKeys.WECHAT_ACCESS_TOKEN, accessToken, expireTime);
        redisClientService.set(RedisKeys.WECHAT_OPENID + code + "_", openId);
    }

    /**
     * 判断access_token是否失效
     *
     * @param accessToken
     * @param openId
     * @return
     */
    private boolean judgingAccessToken(String accessToken, String openId, String code) {
        if (StringUtils.isEmpty(accessToken) || StringUtils.isEmpty(openId) || StringUtils.isEmpty(code)) {
            return false;
        }
        String url = "https://api.weixin.qq.com/sns/auth?" +
                "access_token=" + accessToken +
                "&openid=" + openId;
        JSONObject jsonObject = getJsonObject(url);
        if (jsonObject == null) {
            return false;
        }
        String errmsg = jsonObject.getString("errmsg");
        if (!"ok".equals(errmsg)) {
            redisClientService.remove(RedisKeys.WECHAT_OPENID + code + "_");
            redisClientService.remove("nickname_" + code + "_");
            redisClientService.remove("sex_" + code + "_");
            redisClientService.remove("headimgurl_" + code + "_");
            return false;
        }
        return true;
    }

    /**
     * 获得微信返回值
     *
     * @param url
     * @return
     */
    private JSONObject getJsonObject(String url) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        String body = responseEntity.getBody();
        return JSON.parseObject(body);
    }

    /**
     * 微信用户实体
     */
    @Data
    private class UserInfo {
        /**
         * 用户昵称
         */
        String nickname;
        /**
         * 用户性别（1：男，2：女，0：未知)
         */
        String sex;
        /**
         * 用户头像
         */
        String headImgUrl;
    }

    /**
     * 验证微信消息，成为开发者
     *
     * @param request
     * @return
     */
    @RequestMapping("/wechat/check")
    public String checkSignature(HttpServletRequest request) {
        String echostr = request.getParameter("echostr");
        if (isSignature(request)) {
            return echostr;
        }
        return null;
    }

    private boolean isSignature(HttpServletRequest request) {
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String[] arr = new String[]{timestamp, nonce, TOKEN};
        Arrays.sort(arr);
        String s = arr[0] + arr[1] + arr[2];
        MessageDigest md;
        byte[] digest = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
            digest = md.digest(s.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert digest != null;
        String sign = bytesToHexString(digest);
        return signature.equals(sign);
    }


    private static String bytesToHexString(byte[] bArray) {
        StringBuilder sb = new StringBuilder(bArray.length);
        String sTemp;
        for (byte b : bArray) {
            sTemp = Integer.toHexString(0xFF & b);
            if (sTemp.length() < 2) {
                sb.append(0);
            }
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString().toLowerCase();
    }


}
