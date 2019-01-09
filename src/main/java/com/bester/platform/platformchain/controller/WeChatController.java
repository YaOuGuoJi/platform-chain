package com.bester.platform.platformchain.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bester.platform.platformchain.common.CommonResult;
import com.bester.platform.platformchain.enums.HttpStatus;
import com.google.common.collect.Maps;
import com.xianbester.api.constant.RedisKeys;
import com.xianbester.api.service.RedisClientService;
import lombok.Data;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    private static final String COOKIE_KEY = "cookie";
    private static final String APP_ID = "wx1f67f5bad1eb9ebb";
    private static final String APP_SECRET = "ee51f467fdb53ec6da449db05e733cc1";
    private static final String TOKEN = "bester";
    private static final String SCOPE = "snsapi_userinfo";
    private static final String ENCODING_AES_KEY = "lnuH1Yeea8STf7cmnEVUQxUae4URLcNEWzjekO0AYpr";

    @Reference
    private RedisClientService redisClientService;

    /**
     * 重定向至微信授权页面
     *
     * @return
     * @throws UnsupportedEncodingException
     */
    @GetMapping("/wechat/authorization")
    public String getCode() throws UnsupportedEncodingException {
        String redirectUrl = "https://wechat.xianbester.com/home";
        return "https://open.weixin.qq.com/connect/oauth2/authorize?" +
                "appid=" + APP_ID +
                "&redirect_uri=" + URLEncoder.encode(redirectUrl, "UTF-8") +
                "&response_type=code" +
                "&scope=" + SCOPE +
                "&state=STATE#wechat_redirect";
    }

    /**
     * 用户授权之后返回用户openId
     *
     * @param code
     * @return
     */
    @GetMapping("/wechat/openId")
    public CommonResult addUserInfo(String code, HttpServletResponse response) {
        if (StringUtils.isEmpty(code)) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        String openId = getTokenAndOpenId(code);
        System.out.println("【/wechat/openId】--------------------------->" + openId);
        if (StringUtils.isEmpty(openId)) {
            return CommonResult.fail(HttpStatus.ERROR);
        }
        Cookie cookie = new Cookie(COOKIE_KEY, openId);
        cookie.setPath("/");
        cookie.setMaxAge(30 * 24 * 3600);
        response.addCookie(cookie);
        return CommonResult.success();
    }

    /**
     * 根据用户openId获取微信用户信息
     *
     * @return
     */
    @GetMapping("/wechat/userInfo")
    public CommonResult userInfo(HttpServletRequest request) {
        String openId = "";
        Cookie[] cookies = request.getCookies();
        if (ArrayUtils.isNotEmpty(cookies)) {
            for (Cookie cookie : cookies) {
                if (COOKIE_KEY.equals(cookie.getName())) {
                    openId = cookie.getValue();
                }
            }
        }
        if (StringUtils.isEmpty(openId)) {
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        System.out.println("【/wechat/userInfo】------------------------->" + openId);
        String accessToken = (String) redisClientService.get(RedisKeys.WECHAT_ACCESS_TOKEN);
        if (StringUtils.isEmpty(accessToken) || judgingAccessToken(accessToken, openId)) {
            String refreshToken = (String) redisClientService.get(RedisKeys.WECHAT_REFRESH_TOKEN);
            if (StringUtils.isEmpty(refreshToken)) {
                return CommonResult.fail(HttpStatus.NOT_FOUND);
            }
            accessToken = refreshAccessToken(refreshToken);
        }
        if (StringUtils.isEmpty(accessToken)) {
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        JSONObject userInfoJson = getUserInfoJson(accessToken, openId);
        if (userInfoJson == null) {
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        String nickname = new String(userInfoJson.getString("nickname").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        String sex = userInfoJson.getString("sex");
        String headImgUrl = userInfoJson.getString("headimgurl");
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
    private String getTokenAndOpenId(String code) {
        String getAccessTokenAndOpenIdUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?" +
                "appid=" + APP_ID +
                "&secret=" + APP_SECRET +
                "&code=" + code +
                "&grant_type=authorization_code";
        JSONObject jsonObject = getJsonObject(getAccessTokenAndOpenIdUrl);
        System.out.println("【json】" + jsonObject);
        if (jsonObject == null) {
            return "";
        }
        String accessToken = jsonObject.getString("access_token");
        String openId = jsonObject.getString("openid");
        Long expireIn = jsonObject.getLongValue("expires_in");
        String refreshToken = jsonObject.getString("refresh_token");
        Long expireTime = (expireIn - 5 * 60) * 1000L;
        redisClientService.set(RedisKeys.WECHAT_ACCESS_TOKEN, accessToken, expireTime);
        redisClientService.set(RedisKeys.WECHAT_REFRESH_TOKEN, refreshToken, 30 * 24 * 3600 * 1000L);
        return openId;
    }

    /**
     * 判断access_token是否失效
     *
     * @param accessToken
     * @param openId
     * @return
     */
    private boolean judgingAccessToken(String accessToken, String openId) {
        if (StringUtils.isEmpty(accessToken) || StringUtils.isEmpty(openId)) {
            return false;
        }
        String url = "https://api.weixin.qq.com/sns/auth?" +
                "access_token=" + accessToken +
                "&openid=" + openId;
        JSONObject jsonObject = getJsonObject(url);
        if (jsonObject == null) {
            return false;
        }
        String errMsg = jsonObject.getString("errmsg");
        return "ok".equals(errMsg);
    }

    /**
     * access_token失效之后使用refresh_token刷新
     *
     * @param refreshToken
     * @return
     */
    private String refreshAccessToken(String refreshToken) {
        String url = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid="
                + APP_ID + "&grant_type=refresh_token&refresh_token=" + refreshToken;
        JSONObject jsonObject = getJsonObject(url);
        if (jsonObject == null || StringUtils.isNotBlank(jsonObject.getString("errcode"))) {
            return "";
        }
        String accessToken = jsonObject.getString("access_token");
        String expireIn = jsonObject.getString("expires_in");
        refreshToken = jsonObject.getString("refresh_token");
        Long expireTime = (Long.valueOf(expireIn) - 5 * 60) * 1000L;
        redisClientService.set(RedisKeys.WECHAT_ACCESS_TOKEN, accessToken, expireTime);
        redisClientService.set(RedisKeys.WECHAT_REFRESH_TOKEN, refreshToken, 30 * 24 * 3600 * 1000L);
        return accessToken;
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
    @GetMapping("/wechat/check")
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
