package com.bester.platform.platformchain.controller;

import com.bester.platform.platformchain.common.CommonResult;
import com.bester.platform.platformchain.dto.IDCardDTO;
import com.bester.platform.platformchain.dto.UserInfoDTO;
import com.bester.platform.platformchain.enums.HttpStatus;
import com.bester.platform.platformchain.service.IdentityCardService;
import com.bester.platform.platformchain.service.UserInfoService;
import com.bester.platform.platformchain.util.UserInfoUtil;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

/**
 * @author liuwen
 * @date 2018/12/18
 */
@RestController
public class IdentityController {

    private static final Logger LOGGER = LoggerFactory.getLogger(IdentityController.class);

    private static final Set<String> IMAGE_TYPES = Sets.newHashSet("PNG", "JPG", "JPEG", "BMP");

    @Resource
    private IdentityCardService identityCardService;
    @Resource
    private UserInfoService userInfoService;

    @PostMapping("/user/identityCard")
    public CommonResult uploadIdentityCard(MultipartFile image) {
        int userId = UserInfoUtil.getUserId();
        if (userId <= 0) {
            return CommonResult.fail(HttpStatus.UNAUTHORIZED);
        }
        UserInfoDTO userInfoDTO = userInfoService.findUserInfoByUserId(userId);
        if (StringUtils.isNotEmpty(userInfoDTO.getIdentityId())) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR.value, "您已进行过实名认证！");
        }
        if (image == null || image.isEmpty()) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR.value, "图片为空！");
        }
        String originalFilename = image.getOriginalFilename();
        if (StringUtils.isEmpty(originalFilename)) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR.value, "文件名为空！");
        }
        String imageType = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        if (!IMAGE_TYPES.contains(imageType.toUpperCase())) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR.value, "不支持的文件格式！");
        }
        byte[] bytes;
        try {
            bytes = image.getBytes();
        } catch (IOException e) {
            LOGGER.error("转byte数组失败！", e);
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR.value, "解析文件失败！");
        }
        IDCardDTO idCardDTO = identityCardService.idCardOCR(bytes);
        if (!validFields(idCardDTO)) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR.value, "识别信息不完整，请重新上传照片！");
        }
        return bindIdentityCard2User(userInfoDTO, idCardDTO);
    }

    private CommonResult bindIdentityCard2User(UserInfoDTO userInfoDTO, IDCardDTO idCardDTO) {
        userInfoDTO.setUserName(idCardDTO.getName());
        userInfoDTO.setSex("男".equals(idCardDTO.getSex()) ? 1 : 2);
        userInfoDTO.setNationality(idCardDTO.getNationality());
        userInfoDTO.setAddress(idCardDTO.getAddress());
        userInfoDTO.setIdentityId(idCardDTO.getIdentityId());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        try {
            Date birthday = sdf.parse(idCardDTO.getBirthday());
            userInfoDTO.setBirthday(birthday);
        } catch (ParseException e) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR.value, "生日格式错误！");
        }
        int i = userInfoService.bindIdentityInfo(userInfoDTO);
        return i <= 0 ? CommonResult.fail(HttpStatus.ERROR.value, "绑定身份证失败！") : CommonResult.success();
    }

    private boolean validFields(IDCardDTO idCardDTO) {
        try {
            for (Field field : idCardDTO.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if (field.get(idCardDTO) == null) {
                    return false;
                }
            }
        } catch (IllegalAccessException e) {
            LOGGER.error("反射获取属性值错误！", e);
            return false;
        }
        return true;
    }
}
