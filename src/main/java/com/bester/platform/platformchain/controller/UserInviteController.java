package com.bester.platform.platformchain.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bester.platform.platformchain.common.CommonResult;
import com.bester.platform.platformchain.enums.HttpStatus;
import com.bester.platform.platformchain.util.UserInfoUtil;
import com.xianbester.api.constant.UserInviteConstant;
import com.xianbester.api.dto.UserAccountDTO;
import com.xianbester.api.service.UserAccountService;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liuwen
 * @date 2018/11/28
 */
@RestController
public class UserInviteController {

    @Reference
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
