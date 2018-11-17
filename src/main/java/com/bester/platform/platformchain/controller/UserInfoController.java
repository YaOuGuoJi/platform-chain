package com.bester.platform.platformchain.controller;

import com.bester.platform.platformchain.common.CommonResult;
import com.bester.platform.platformchain.dto.UserAccountDTO;
import com.bester.platform.platformchain.dto.UserInfoDTO;
import com.bester.platform.platformchain.enums.HttpStatus;
import com.bester.platform.platformchain.service.UserAccountService;
import com.bester.platform.platformchain.service.UserInfoService;
import com.bester.platform.platformchain.util.UserInfoUtil;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;
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
    @Resource
    private UserAccountService userAccountService;

    @PostMapping("/user/updateInfo")
    public CommonResult updateUserInfo(UserInfoVO userInfoVO) {
        CommonResult validResult = validParams(userInfoVO);
        if (!validResult.isSuccess()) {
            return validResult;
        }
        int userId = UserInfoUtil.getUserId();
        if (userId < 0) {
            return CommonResult.fail(HttpStatus.UNAUTHORIZED);
        }
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setUserId(userId);
        BeanUtils.copyProperties(userInfoVO, userInfoDTO);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date birth = sdf.parse(userInfoVO.getBirthday());
            userInfoDTO.setBirthday(birth);
        } catch (ParseException e) {
            return CommonResult.fail(403, "生日输入不合法！");
        }
        int result = userInfoService.updateUserInfo(userInfoDTO);
        return result > 0 ? CommonResult.success() : CommonResult.fail(HttpStatus.ERROR);
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

    private CommonResult validParams(UserInfoVO userInfoVO) {
        if (userInfoVO == null || StringUtils.isEmpty(userInfoVO.getUserName())) {
            return CommonResult.fail(403, "用户名不得为空！");
        }
        int userId = UserInfoUtil.getUserId();
        UserAccountDTO userAccountDTO = userAccountService.findUserAccountInfoByUserName(userInfoVO.userName);
        if (userAccountDTO != null && userId != userAccountDTO.getUserId()) {
            return CommonResult.fail(403, "用户名已被占用！");
        }
        if (userInfoVO.sex == null || userInfoVO.sex <= 0) {
            return CommonResult.fail(403, "性别不合法");
        }
        if (StringUtils.isEmpty(userInfoVO.phone) || userInfoVO.phone.length() > 20) {
            return CommonResult.fail(403, "手机号不合法！");
        }
        if (StringUtils.isEmpty(userInfoVO.email) || userInfoVO.email.length() > 30) {
            return CommonResult.fail(403, "email不合法！");
        }
        if (StringUtils.isEmpty(userInfoVO.address) || userInfoVO.address.length() > 50) {
            return CommonResult.fail(403, "地址为空或超长！");
        }
        return CommonResult.success();
    }

    @Data
    public class UserInfoVO {
        /**
         * 用户姓名
         */
        private String userName;

        /**
         * 性别 1-男 2-女 其他-未知
         */
        private Integer sex;

        /**
         * 生日 yyyy-MM-dd
         */
        private String birthday;

        /**
         * 手机号
         */
        private String phone;

        /**
         * 邮箱
         */
        private String email;

        /**
         * 地址
         */
        private String address;

        /**
         * 职业/工作
         */
        private String job;
    }
}
