package com.bester.platform.platformchain.controller;

import com.bester.platform.platformchain.common.CommonResult;
import com.bester.platform.platformchain.constant.PowerSource;
import com.bester.platform.platformchain.constant.PowerStatus;
import com.bester.platform.platformchain.constant.RedisKeys;
import com.bester.platform.platformchain.dto.UserInfoDTO;
import com.bester.platform.platformchain.enums.HttpStatus;
import com.bester.platform.platformchain.service.PowerRecordService;
import com.bester.platform.platformchain.service.UserInfoService;
import com.bester.platform.platformchain.util.RandomUtil;
import com.bester.platform.platformchain.util.UserInfoUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bester.platform.platformchain.service.RedisClientService;
import javax.annotation.Resource;

/**
 * @author lizhi
 */
@RestController
public class BindPublicNumController {
    @Resource
    private RedisClientService redisClientService;

    @Resource
    private PowerRecordService powerRecordService;

    @Resource
    private UserInfoService userInfoService;

    /**
     * 公众号获取验证码
     * @return
     */
    @GetMapping("/user/getVerificationCode")
    public  CommonResult getVerificationCode(){
        String randomNum = RandomUtil.charAndNumberRandom(4);
        String randomNewNum = characterConversion(randomNum);
        redisClientService.set(randomNewNum,randomNum,180000L);
        return CommonResult.success(randomNum);
    }

    /**
     * APP验证公众号绑定
     * @param verificationCode
     * @return
     */
    @PostMapping("/user/checkVerificationCode")
    public  CommonResult checkVerificationCode(String verificationCode) {
        int userId = UserInfoUtil.getUserId();
        UserInfoDTO userInfoDTO = userInfoService.findUserInfoByUserId(userId);
        if (userInfoDTO == null) {
            return CommonResult.fail(HttpStatus.UNAUTHORIZED);
        }
        if (userInfoDTO.getBindPublicNum() == 1) {
            return CommonResult.fail(403, "您已绑定过公众号");
        }
        if (StringUtils.isBlank(verificationCode)) {
            return CommonResult.fail(403, "请正确输入");
        }
        if (!redisClientService.exists(characterConversion(verificationCode))) {
            return CommonResult.fail(403,"验证码错误，或已过期");
        }
        powerRecordService.addUserPower(userId, PowerSource.BINDPUBLIC, PowerSource.BINDPUBLIC_POWER, PowerStatus.NO_TEMPORARY);
        userInfoService.updateUserBindPublicNum(userId, 1);
        return CommonResult.success("");
    }


    private String characterConversion(String character){
        StringBuilder randomNumBuilder = new StringBuilder(character);
        randomNumBuilder.insert(0,RedisKeys.BINDPUBLNUM_VERIFY_CODE);
        return randomNumBuilder.toString();
    }

}
