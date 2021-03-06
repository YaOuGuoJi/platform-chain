package com.bester.platform.platformchain.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bester.platform.platformchain.common.CommonResult;
import com.bester.platform.platformchain.enums.HttpStatus;
import com.bester.platform.platformchain.util.RandomUtil;
import com.bester.platform.platformchain.util.UserInfoUtil;
import com.xianbester.api.constant.PowerSource;
import com.xianbester.api.constant.PowerStatus;
import com.xianbester.api.constant.RedisKeys;
import com.xianbester.api.dto.UserInfoDTO;
import com.xianbester.api.service.PowerRecordService;
import com.xianbester.api.service.RedisClientService;
import com.xianbester.api.service.UserInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lizhi
 */
@RestController
public class BindPublicNumController {
    @Reference
    private PowerRecordService powerRecordService;
    @Reference
    private RedisClientService redisClientService;
    @Reference
    private UserInfoService userInfoService;

    /**
     * APP验证公众号绑定
     *
     * @param verificationCode
     * @return
     */
    @PostMapping("/user/weChat/verification")
    public CommonResult checkVerificationCode(String verificationCode) {
        int userId = UserInfoUtil.getUserId();
        UserInfoDTO userInfoDTO = userInfoService.findUserInfoByUserId(userId);
        String code = characterConversion(verificationCode);
        if (userInfoDTO == null) {
            return CommonResult.fail(HttpStatus.UNAUTHORIZED);
        }
        if (userInfoDTO.getBindPublicNum() == 1) {
            return CommonResult.fail(403, "您已绑定过公众号");
        }
        if (StringUtils.isBlank(verificationCode)) {
            return CommonResult.fail(403, "请正确输入验证码");
        }
        if (redisClientService.exists(code)) {
            redisClientService.remove(code);
            powerRecordService.addUserPower(userId, PowerSource.BIND_WECHAT, PowerSource.BIND_WECHAT_POWER, PowerStatus.NO_TEMPORARY);
            userInfoService.updateUserBindPublicNum(userId, 1);
            return CommonResult.success("");

        }
        return CommonResult.fail(403, "验证码错误，或验证码已超时");
    }

    private String characterConversion(String character) {
        return RedisKeys.WECHAT_VERIFY_CODE + character;
    }

    /**
     * 公众号获取验证码
     *
     * @return
     */
    @GetMapping("/user/weChat/verifyCode")
    public CommonResult getVerificationCode() {
        String randomNum = RandomUtil.charAndNumberRandom(4);
        String randomNewNum = characterConversion(randomNum);
        redisClientService.set(randomNewNum, randomNum, 180000L);
        return CommonResult.success(randomNum);
    }
}
