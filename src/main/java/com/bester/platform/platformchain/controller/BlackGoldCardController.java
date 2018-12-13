package com.bester.platform.platformchain.controller;

import com.bester.platform.platformchain.common.CommonResult;
import com.bester.platform.platformchain.dto.BlackGoldCardDTO;
import com.bester.platform.platformchain.dto.UserInfoDTO;
import com.bester.platform.platformchain.enums.HttpStatus;
import com.bester.platform.platformchain.enums.UserVipLevel;
import com.bester.platform.platformchain.service.BlackGoldCardService;
import com.bester.platform.platformchain.service.UserInfoService;
import com.bester.platform.platformchain.util.UserInfoUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.regex.Pattern;

/**
 * @author liuwen
 * @date 2018/12/11
 */
@RestController
public class BlackGoldCardController {

    @Resource
    private BlackGoldCardService blackGoldCardService;
    @Resource
    private UserInfoService userInfoService;

    @PostMapping("/card/bind")
    public CommonResult bindCard(String cardId, String password) {
        int userId = UserInfoUtil.getUserId();
        if (userId <= 0) {
            return CommonResult.fail(HttpStatus.UNAUTHORIZED);
        }
        UserInfoDTO userInfoDTO = userInfoService.findUserInfoByUserId(userId);
        if (userInfoDTO.getVip() == UserVipLevel.BlackGold.level) {
            return CommonResult.success("您已成为黑金会籍！");
        }
        if (!validParams(cardId, password)) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        String passwordRegex = "([a-z]|[A-Z]|[0-9]){8}";
        if (!Pattern.matches(passwordRegex, password)) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR.value, "密码错误");
        }
        BlackGoldCardDTO card = blackGoldCardService.findBlackGoldCardByCardId(cardId);
        if (card == null || card.getStatus() != 0 || card.getUserId() != 0) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR.value, "卡号无效！");
        }
        if (!card.getPassword().equals(password)) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR.value, "密码错误!");
        }
        int result = blackGoldCardService.bindCardToUser(cardId, userId);
        if (result <= 0) {
            return CommonResult.fail(HttpStatus.ERROR.value, "绑定失败！");
        }
        userInfoService.updateUserVipLevel(userId, UserVipLevel.BlackGold);
        return CommonResult.success("升级成功！");
    }

    private boolean validParams(String cardId, String password) {
        return StringUtils.isNotEmpty(cardId) && StringUtils.isNotEmpty(password);
    }
}
