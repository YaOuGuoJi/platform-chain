package com.bester.platform.platformchain.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bester.platform.platformchain.common.CommonResult;
import com.bester.platform.platformchain.enums.HttpStatus;
import com.bester.platform.platformchain.util.UserInfoUtil;
import com.google.common.collect.Maps;
import com.xianbester.api.dto.BlackGoldCardDTO;
import com.xianbester.api.dto.UserInfoDTO;
import com.xianbester.api.dto.VoucherCardDTO;
import com.xianbester.api.enums.UserVipLevel;
import com.xianbester.api.service.BlackGoldCardService;
import com.xianbester.api.service.UserInfoService;
import com.xianbester.api.service.VoucherCardService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author liuwen
 * @date 2018/12/11
 */
@RestController
public class CardBindController {

    @Reference
    private BlackGoldCardService blackGoldCardService;
    @Reference
    private UserInfoService userInfoService;
    @Reference
    private VoucherCardService voucherCardService;

    /**
     * 绑定黑金会籍
     *
     * @param cardId
     * @param password
     * @return
     */
    @PostMapping("/blackGold/bind")
    public CommonResult bindCard(String cardId, String password) {
        int userId = UserInfoUtil.getUserId();
        if (userId <= 0) {
            return CommonResult.fail(HttpStatus.UNAUTHORIZED);
        }
        UserInfoDTO userInfoDTO = userInfoService.findUserInfoByUserId(userId);
        if (userInfoDTO.getVip() == UserVipLevel.BlackGold.level) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR.value, "您已成为黑金会籍！");
        }
        if (!validParams(cardId, password)) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        String passwordRegex = "([a-z]|[A-Z]|[0-9]){8}";
        if (!Pattern.matches(passwordRegex, password)) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR.value, "密码错误");
        }
        String cardRegex = ".{12}";
        if (!Pattern.matches(cardRegex, cardId)) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR.value, "卡号错误");
        }
        cardId = cardId.toUpperCase();
        password = password.toUpperCase();
        BlackGoldCardDTO card = blackGoldCardService.findBlackGoldCardByCardId(cardId);
        if (card == null || card.getStatus() != 0 || card.getUserId() != 0) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR.value, "卡号无效！");
        }
        String pwdMD5 = DigestUtils.md5Hex(password);
        if (!card.getPassword().equalsIgnoreCase(pwdMD5)) {
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

    /**
     * 绑定优惠凭证
     *
     * @param cardId
     * @return
     */
    @PostMapping("/voucher/bind")
    public CommonResult bindVoucherCard(String cardId) {
        cardId = cardId.toUpperCase();
        int userId = UserInfoUtil.getUserId();
        if (userId <= 0) {
            return CommonResult.fail(HttpStatus.UNAUTHORIZED);
        }
        VoucherCardDTO voucherCardDTO = voucherCardService.findVoucherCardById(cardId);
        if (voucherCardDTO == null || voucherCardDTO.getStatus() != 0 || voucherCardDTO.getUserId() != 0) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR.value, "卡号无效！");
        }
        int result = voucherCardService.bindVoucherCard2User(cardId, userId);
        if (result <= 0) {
            return CommonResult.fail(HttpStatus.ERROR.value, "绑定失败！");
        }
        return CommonResult.success();
    }

    /**
     * 查询用户黑金卡ID
     *
     * @return
     */
    @GetMapping("/blackGold/cardId")
    public CommonResult getBlackGoldId() {
        int userId = UserInfoUtil.getUserId();
        if (userId < 0) {
            return CommonResult.fail(HttpStatus.UNAUTHORIZED);
        }
        BlackGoldCardDTO blackGoldCardInfo = blackGoldCardService.findBlackGoldCardInfoByUserId(userId);
        if (blackGoldCardInfo == null) {
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        Map<String, String> map = Maps.newHashMap();
        DateTime dateTime = new DateTime(blackGoldCardInfo.getAddTime());
        int year = dateTime.getYear();
        int monthOfYear = dateTime.getMonthOfYear();
        String yearAndMonth = year + "" + monthOfYear;
        map.put("cardId", blackGoldCardInfo.getCardId());
        map.put("bindTime", yearAndMonth);
        return CommonResult.success(map);
    }

}
