package com.bester.platform.platformchain.controller;

import com.bester.platform.platformchain.common.CommonResult;
import com.bester.platform.platformchain.common.CommonResultBuilder;
import com.bester.platform.platformchain.enums.HttpStatus;
import com.bester.platform.platformchain.service.UserAccountService;
import com.bester.platform.platformchain.util.TokenUtil;
import com.bester.platform.platformchain.util.UserInfoUtil;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * @author liuwen
 * @date 2018/11/6
 */
@RestController
public class LoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Resource
    private UserAccountService userAccountService;

    @GetMapping("/user/isLogin")
    public CommonResult isLogin(HttpServletRequest request) {
        String token = TokenUtil.getToken(request);
        try {
            TokenUtil.verifyToken(token);
        } catch (Exception e) {
            return CommonResult.success(false);
        }
        return CommonResult.success(true);
    }

    @PostMapping("/user/login")
    public CommonResult login(int userId, String password, HttpServletResponse response) {
        if (userId <= 0 || StringUtils.isEmpty(password)) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        if (!userAccountService.checkUserIdExist(userId)) {
            return CommonResult.fail(403, "不存在该用户！");
        }
        boolean match = userAccountService.checkUserPassword(userId, password);
        if (!match) {
            return CommonResult.fail(403, "验证失败");
        }
        try {
            Map<String, String> data = Maps.newHashMap();
            data.put("userId", String.valueOf(userId));
            TokenUtil.updateToken2Cookie(response, data);
        } catch (Exception e) {
            LOGGER.error("token加密失败！", e);
            return CommonResult.fail(HttpStatus.ERROR);
        }
        userAccountService.addLoginRecord(userId);
        return new CommonResultBuilder().code(200).message("登录成功").data("userId", userId).build();
    }

    @PostMapping("/user/logout")
    public CommonResult logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("token", "");
        cookie.setPath("/");
        response.addCookie(cookie);
        return new CommonResultBuilder().code(200).message("退出成功").build();
    }

}
