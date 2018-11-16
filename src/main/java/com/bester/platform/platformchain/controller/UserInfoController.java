package com.bester.platform.platformchain.controller;

import com.bester.platform.platformchain.common.CommonResult;
import com.bester.platform.platformchain.common.CommonResultBuilder;
import com.bester.platform.platformchain.dto.UserInfoDTO;
import com.bester.platform.platformchain.enums.HttpStatus;
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

    @PostMapping("/user/updateInfo")
    public CommonResult updateUserInfo(UserInfoVO userInfoVO) {
        String userName = userInfoVO.getUserName();
        if (userInfoVO.getUserId() == null || StringUtils.isEmpty(userName)) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        if (userInfoVO.getAddress().length() > 30) {
            return CommonResult.fail(403, "地址超出最大长度");
        }
        if (userInfoVO.getEmail().length() > 20) {
            return CommonResult.fail(403, "地址超出最大长度");
        }
        if (userInfoVO.getPhone().length() > 20) {
            return CommonResult.fail(403, "地址超出最大长度");
        }
        if (userInfoVO.getJob().length() > 30) {
            return CommonResult.fail(403, "地址超出最大长度");
        }
        String birthday = userInfoVO.getBirthday();
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        BeanUtils.copyProperties(userInfoVO, userInfoDTO);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date birth = sdf.parse(birthday);
            userInfoDTO.setBirthday(birth);
            int result = userInfoService.updateUserInfo(userInfoDTO);
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

    @Data
    class UserInfoVO {
        /**
         * 添加时间
         */
        private Date addTime;
        /**
         * 地址
         */
        private String address;
        /**
         * 生日
         */
        private String birthday;
        /**
         * 汽车id
         */
        private Integer carId;
        /**
         * 邮箱
         */
        private String email;
        /**
         * 身份证号码
         */
        private String identityId;
        /**
         * 职业/工作
         */
        private String job;
        /**
         * 手机号
         */
        private String phone;
        /**
         * 性别 1-男 2-女 其他-未知
         */
        private Integer sex;
        /**
         * 更新时间
         */
        private Date updateTime;
        /**
         * 用户id
         */
        private Integer userId;
        /**
         * 用户姓名
         */
        private String userName;
        /**
         * 是否VIP用户 1-是 0-不是
         */
        private Integer vip;
    }
}
