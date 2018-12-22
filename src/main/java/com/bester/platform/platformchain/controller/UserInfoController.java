package com.bester.platform.platformchain.controller;

import com.bester.platform.platformchain.common.CommonResult;
import com.bester.platform.platformchain.dto.UserInfoDTO;
import com.bester.platform.platformchain.dto.VoucherCardDTO;
import com.bester.platform.platformchain.enums.HttpStatus;
import com.bester.platform.platformchain.service.UserInfoService;
import com.bester.platform.platformchain.service.VoucherCardService;
import com.bester.platform.platformchain.util.UserInfoUtil;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author liuwen
 * @date 2018/11/12
 */
@RestController
public class UserInfoController {

    @Resource
    private UserInfoService userInfoService;

    @Resource
    private VoucherCardService voucherCardService;

    /**
     * 修改用户信息
     *
     * @param email
     * @param job
     * @return
     */
    @PostMapping("/user/updateInfo")
    public CommonResult updateUserInfo(String email, String job) {
        String emailRegex = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$";
        if (!Pattern.matches(emailRegex, email) || StringUtils.isEmpty(job)) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        int userId = UserInfoUtil.getUserId();
        if (userId < 0) {
            return CommonResult.fail(HttpStatus.UNAUTHORIZED);
        }
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setUserId(userId);
        userInfoDTO.setEmail(email);
        userInfoDTO.setJob(job);
        int result = userInfoService.updateUserInfo(userInfoDTO);
        return result > 0 ? CommonResult.success() : CommonResult.fail(HttpStatus.ERROR);
    }

    /**
     * 查询用户详情
     *
     * @return
     */
    @GetMapping("/user/detail")
    public CommonResult<Membership> userInfo() {
        int userId = UserInfoUtil.getUserId();
        UserInfoDTO userInfoDTO = userInfoService.findUserInfoByUserId(userId);
        if (userInfoDTO == null) {
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        List<VoucherCardDTO> voucherCardDTOList = voucherCardService.findUserBindVouchers(userId);
        BigDecimal amount = plusAmount(voucherCardDTOList);
        Membership membership = new Membership();
        membership.setUserName(userInfoDTO.getUserName());
        membership.setVipLevel(userInfoDTO.getVip());
        membership.setPhone(userInfoDTO.getPhone().replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2"));
        membership.setIdentityId(userInfoDTO.getIdentityId());
        membership.setVoucherAmount(amount);
        return CommonResult.success(membership);
    }

    private BigDecimal plusAmount(List<VoucherCardDTO> voucherCardDTOList) {
        if (CollectionUtils.isEmpty(voucherCardDTOList)) {
            return BigDecimal.ZERO;
        }
        return voucherCardDTOList.stream().map(VoucherCardDTO::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Data
    public class Membership {

        /**
         * 会籍
         */
        private Integer vipLevel;

        /**
         * 姓名
         */
        private String userName;

        /**
         * 电话
         */
        private String phone;

        /**
         * 身份证号
         */
        private String identityId;

        /**
         * 代金券余额
         */
        private BigDecimal voucherAmount;

    }

}
