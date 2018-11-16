package com.bester.platform.platformchain.controller;

import com.bester.platform.platformchain.common.CommonResult;
import com.bester.platform.platformchain.common.CommonResultBuilder;
import com.bester.platform.platformchain.dto.UserInfoDTO;
import com.bester.platform.platformchain.enums.HttpStatus;
import com.bester.platform.platformchain.service.UserInfoService;
import com.bester.platform.platformchain.util.UserInfoUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author liuwen
 * @date 2018/11/12
 */
@RestController
public class UserInfoController {

    @Resource
    private UserInfoService userInfoService;

    @PostMapping("/user/updateInfo")
    public CommonResult updateUserInfo(int userId,
                                       String birthday,
                                       String userName,
                                       int sex,
                                       String phone,
                                       String email,
                                       String address,
                                       String job) {
        SimpleDateFormat saf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date birth = saf.parse(birthday);
            int result = userInfoService.updateUserInfo(userId, birth, userName, sex, phone, email, address, job);
            if (result > 0) {
                return new CommonResultBuilder()
                        .code(200)
                        .message("修改成功").build();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return CommonResult.fail(403, "失败");
    }

    @GetMapping("/user/detail")
    public CommonResult<UserInfoDTO> userInfo() {
        int userId = UserInfoUtil.getUserId();
        UserInfoDTO userInfoDTO = userInfoService.findUserInfoByUserId(userId);
        if (userInfoDTO == null) {
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        return CommonResult.success(userInfoDTO);
    }


}
