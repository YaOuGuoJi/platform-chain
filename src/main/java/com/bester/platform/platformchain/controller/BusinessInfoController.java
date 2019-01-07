package com.bester.platform.platformchain.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bester.platform.platformchain.common.CommonResult;
import com.bester.platform.platformchain.enums.HttpStatus;
import com.xianbester.api.dto.BusinessInfoDTO;
import com.xianbester.api.service.BusinessInfoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yanrui
 */
@RestController
public class BusinessInfoController {

    @Reference
    private BusinessInfoService businessInfoService;

    @GetMapping("/businessInfo")
    public CommonResult insertBusinessInfo(String registerPhone, String name, String phone, String weChat,
                                           String introduce, Integer type, String remarks){
        if (registerPhone == null || name == null || phone == null || weChat == null || introduce == null ||
                type == null || remarks == null) {
            CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        BusinessInfoDTO businessInfoDTO = new BusinessInfoDTO();
        businessInfoDTO.setRegisterPhone(registerPhone);
        businessInfoDTO.setBusinessName(name);
        businessInfoDTO.setPhone(phone);
        businessInfoDTO.setWeChat(weChat);
        businessInfoDTO.setIntroduce(introduce);
        businessInfoDTO.setBusinessType(type);
        businessInfoDTO.setRemarks(remarks);
        businessInfoService.insertBusinessInfo(businessInfoDTO);
        return CommonResult.success();
    }
}
