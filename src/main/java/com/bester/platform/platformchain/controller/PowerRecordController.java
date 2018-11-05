package com.bester.platform.platformchain.controller;

import com.bester.platform.platformchain.common.CommonResult;
import com.bester.platform.platformchain.service.PowerRecordService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author liuwen
 * @date 2018/11/3
 */
@RestController
public class PowerRecordController {

    @Resource
    private PowerRecordService powerRecordService;

    @GetMapping("/user/record/power")
    public CommonResult findPowerRecord(String token) {
        return new CommonResult();
    }
}
