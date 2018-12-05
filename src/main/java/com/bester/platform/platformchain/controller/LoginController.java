package com.bester.platform.platformchain.controller;

import com.bester.platform.platformchain.common.CommonResult;
import com.bester.platform.platformchain.common.CommonResultBuilder;
import com.bester.platform.platformchain.constant.BlockChainParameters;
import com.bester.platform.platformchain.constant.PowerSource;
import com.bester.platform.platformchain.constant.PowerStatus;
import com.bester.platform.platformchain.constant.UserInviteConstant;
import com.bester.platform.platformchain.dto.UserAccountDTO;
import com.bester.platform.platformchain.enums.HttpStatus;
import com.bester.platform.platformchain.service.PowerRecordService;
import com.bester.platform.platformchain.service.SmsClientService;
import com.bester.platform.platformchain.service.UserAccountService;
import com.bester.platform.platformchain.service.UserInfoService;
import com.bester.platform.platformchain.util.InviteCodeUtil;
import com.bester.platform.platformchain.util.TokenUtil;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author liuwen
 * @date 2018/11/6
 */
@RestController
public class LoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Resource
    private UserAccountService userAccountService;

    @Resource
    private PowerRecordService powerRecordService;

    @Resource
    private UserInfoService userInfoService;

    @Resource
    private SmsClientService smsClientService;

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

    @GetMapping("/user/verificationCode")
    public CommonResult sendVerificationCode(String phoneNum) {
        if (StringUtils.isBlank(phoneNum) || phoneNum.length() != BlockChainParameters.PHONE_NUMBER_LENGTH) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        int result = smsClientService.sendVerifyCode(phoneNum);
        if (result == 0) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR.value, "发送验证码失败，请稍后再试");
        }
        return CommonResult.success();
    }

    @PostMapping("/user/verification")
    public CommonResult userVerification(String phoneNum, String code,@RequestParam(required = false, defaultValue = "") String inviteCode, HttpServletResponse response) {
        if (StringUtils.isBlank(phoneNum) || StringUtils.isBlank(code) || phoneNum.length() < BlockChainParameters.PHONE_NUMBER_LENGTH){
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        String regex = "[0-9]{6}";
        if (!Pattern.matches(regex, code)) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        int verifyCode = smsClientService.verifyCode(phoneNum, code);
        if (verifyCode == 0) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR.value, "验证码错误");
        }
        UserAccountDTO userAccountInfo = userAccountService.findUserAccountInfoByPhoneNum(phoneNum);
        if (userAccountInfo != null) {
            try {
                Map<String, String> data = Maps.newHashMap();
                data.put("userId", userAccountInfo.getUserId() + "");
                TokenUtil.updateToken2Cookie(response, data);
            } catch (UnsupportedEncodingException e) {
                LOGGER.error("token加密失败!");
                return CommonResult.fail(HttpStatus.ERROR);
            }
            userAccountService.addLoginRecord(userAccountInfo.getUserId());
            return new CommonResultBuilder().code(200).data("userId", userAccountInfo.getUserId()).build();
        } else {
            UserAccountDTO userAccountDTO = new UserAccountDTO();
            userAccountDTO.setPhoneNum(phoneNum);
            userAccountDTO.setInviteCode(buildUniqueInviteCode());
            int userId = userAccountService.addUserAccountInfo(userAccountDTO);
            if (userId < 0) {
                return CommonResult.fail(HttpStatus.PARAMETER_ERROR.value, "注册失败");
            }
            addPower(userId, inviteCode);
            Map<String, String> data = Maps.newHashMap();
            data.put("userId", userId + "");
            try {
                TokenUtil.updateToken2Cookie(response, data);
            } catch (UnsupportedEncodingException e) {
                LOGGER.error("token生成失败");
                return CommonResult.fail(HttpStatus.ERROR);
            }
            userAccountService.addLoginRecord(userId);
            return CommonResult.success("注册成功");
        }
    }

    @PostMapping("/user/logout")
    public CommonResult logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("token", "");
        cookie.setPath("/");
        response.addCookie(cookie);
        return new CommonResultBuilder().code(200).message("退出成功").build();
    }

    private void addPower(int userId, String inviteCode) {
        powerRecordService.addUserPower(userId, PowerSource.REGISTER, PowerSource.REGISTER_POWER, PowerStatus.NO_TEMPORARY);
        if (StringUtils.isNotBlank(inviteCode)) {
            UserAccountDTO userAccountDTO = userAccountService.findUserAccountInfoByInviteCode(inviteCode);
            if (userAccountDTO != null && userAccountDTO.getInviteTimes() < UserInviteConstant.ALLOWED_INVITE_TIMES) {
                powerRecordService.addUserPower(userAccountDTO.getUserId(), PowerSource.INVITE,
                        PowerSource.INVITE_POWER, PowerStatus.NO_TEMPORARY);
                userAccountService.addUserInviteTimes(userAccountDTO.getUserId());
            }
        }
    }

    private String buildUniqueInviteCode() {
        String inviteCode = null;
        UserAccountDTO userAccountDTO = null;
        while (userAccountDTO == null) {
            inviteCode = InviteCodeUtil.userInviteCode();
            userAccountDTO = userAccountService.findUserAccountInfoByInviteCode(inviteCode);
        }
        return inviteCode;
    }

}
