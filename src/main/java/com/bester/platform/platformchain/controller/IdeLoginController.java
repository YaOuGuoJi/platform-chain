package com.bester.platform.platformchain.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bester.platform.platformchain.common.CommonResult;
import com.bester.platform.platformchain.common.CommonResultBuilder;
import com.bester.platform.platformchain.enums.HttpStatus;
import com.bester.platform.platformchain.util.TokenUtil;
import com.google.common.collect.Maps;
import com.xianbester.api.dto.ChainUserInfoDTO;
import com.xianbester.api.dto.ChainUserLoginRecordDTO;
import com.xianbester.api.service.ChainUserInfoService;
import com.xianbester.api.service.ChainUserLoginRecordService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.UUID;

/**
 * @author zhangqiang
 * @date 2019-07-15
 */
@RestController
public class IdeLoginController {

    @Reference
    private ChainUserInfoService userInfoService;

    @Reference
    private ChainUserLoginRecordService chainUserLoginRecordService;

    @GetMapping("/ide/isLogin")
    public CommonResult isLogin(HttpServletRequest request) {
        String token = TokenUtil.getToken(request);
        try {
            TokenUtil.verifyToken(token);
        } catch (Exception e) {
            return CommonResult.success(false);
        }
        return CommonResult.success(true);
    }

    @PostMapping("/ide/login")
    public CommonResult login(ChainUserInfoDTO dto, HttpServletResponse response) {
        if (dto == null) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        if (StringUtils.isBlank(dto.getUsername()) || StringUtils.isBlank(dto.getPassword())) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        String userId = userInfoService.loginWithUsernameAndPassword(dto.getUsername(), dto.getPassword());
        if (!StringUtils.isBlank(userId)) {
            Map<String, String> data = Maps.newHashMap();
            try {
                data.put("userId", userId);
                TokenUtil.updateToken2Cookie(response, data);
            } catch (UnsupportedEncodingException e) {
                return CommonResult.fail(HttpStatus.ERROR);
            }
        } else {
            ChainUserInfoDTO ChainUserInfoDTO = new ChainUserInfoDTO();
            String id = UUID.randomUUID().toString();
            ChainUserInfoDTO.setId(id);
            ChainUserInfoDTO.setUsername(dto.getUsername());
            ChainUserInfoDTO.setPassword(dto.getPassword());
            int i = userInfoService.addUser(ChainUserInfoDTO);
            if (i < 0) {
                return CommonResult.fail(HttpStatus.PARAMETER_ERROR.value, "注册失败");
            }
            Map<String, String> data = Maps.newHashMap();
            data.put("userId", id);
            try {
                TokenUtil.updateToken2Cookie(response, data);
            } catch (UnsupportedEncodingException e) {
                return CommonResult.fail(HttpStatus.ERROR);
            }
            return CommonResult.success("注册成功");
        }
        return CommonResult.success();
    }

    @PostMapping("/ide/logout")
    public CommonResult logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("token", "");
        cookie.setPath("/");
        response.addCookie(cookie);
        return new CommonResultBuilder().code(200).message("退出成功").build();
    }

    @GetMapping("/ide/check")
    public CommonResult checkUsername(String username) {
        if (StringUtils.isBlank(username)) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        ChainUserInfoDTO userInfoByUsername = userInfoService.findUserInfoByUsername(username);
        if (userInfoByUsername == null) {
            return CommonResult.success(true);
        }
        return CommonResult.success(false);
    }

    @GetMapping("/ide/record")
    public CommonResult loginRecord(String name, String follow) {
        if (StringUtils.isBlank(name) || StringUtils.isBlank(follow)) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        ChainUserLoginRecordDTO dto = new ChainUserLoginRecordDTO();
        String id = UUID.randomUUID().toString();
        dto.setId(id);
        dto.setUsername(name);
        dto.setFollow(follow);
        int i = chainUserLoginRecordService.addUserLoginRecord(dto);
        if (i < 0) {
            return CommonResult.fail("记录失败");
        }
        return CommonResult.success();
    }

}
