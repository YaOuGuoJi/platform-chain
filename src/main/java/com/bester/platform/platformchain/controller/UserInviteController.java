package com.bester.platform.platformchain.controller;

import com.bester.platform.platformchain.common.CommonResult;
import com.bester.platform.platformchain.constant.UserInviteConstant;
import com.bester.platform.platformchain.dto.UserAccountDTO;
import com.bester.platform.platformchain.enums.HttpStatus;
import com.bester.platform.platformchain.service.UserAccountService;
import com.bester.platform.platformchain.util.UserInfoUtil;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author liuwen
 * @date 2018/11/28
 */
@RestController
public class UserInviteController {

    @Resource
    private UserAccountService userAccountService;

    @GetMapping("/user/inviteCode")
    public CommonResult inviteCode() {
        int userId = UserInfoUtil.getUserId();
        UserAccountDTO userAccountDTO = userAccountService.findUserAccountInfoByUserId(userId);
        if (userAccountDTO == null) {
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        InviteVO inviteVO = new InviteVO();
        inviteVO.setInviteCode(userAccountDTO.getInviteCode());
        inviteVO.setInviteTimes(userAccountDTO.getInviteTimes());
        inviteVO.setAllowedTimes(UserInviteConstant.ALLOWED_INVITE_TIMES);
        inviteVO.setRemainTimes(UserInviteConstant.ALLOWED_INVITE_TIMES - userAccountDTO.getInviteTimes());
        return CommonResult.success(inviteVO);
    }

    @Data
    public class InviteVO {
        private String userName;
        private String inviteCode;
        private Integer inviteTimes;
        private Integer allowedTimes;
        private Integer remainTimes;
    }

}
