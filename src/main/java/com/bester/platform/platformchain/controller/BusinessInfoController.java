package com.bester.platform.platformchain.controller;

import com.bester.platform.platformchain.common.CommonResult;
import com.bester.platform.platformchain.dto.BusinessInfoDTO;
import com.bester.platform.platformchain.service.BusinessInfoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author yanrui
 */
@RestController
public class BusinessInfoController {

    @Resource
    private BusinessInfoService businessInfoService;

    @GetMapping("/businessInfo")
    public CommonResult insertBusinessInfo(String registerPhone, String name, String phone, String weChat,
                                           String introduce, Integer type, String remarks){
        BusinessInfoDTO businessInfoDTO = new BusinessInfoDTO();
        businessInfoDTO.setRegisterPhone(registerPhone);
        businessInfoDTO.setName(name);
        businessInfoDTO.setPhone(phone);
        businessInfoDTO.setWeChat(weChat);
        businessInfoDTO.setIntroduce(introduce);
        businessInfoDTO.setType(type);
        businessInfoDTO.setRemarks(remarks);
        businessInfoService.insertBusinessInfo(businessInfoDTO);
        return CommonResult.success();
    }
}
