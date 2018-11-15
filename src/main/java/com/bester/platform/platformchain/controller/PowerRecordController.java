package com.bester.platform.platformchain.controller;

import com.bester.platform.platformchain.common.CommonResult;
import com.bester.platform.platformchain.dto.PowerRecordDTO;
import com.bester.platform.platformchain.enums.HttpStatus;
import com.bester.platform.platformchain.service.PowerRecordService;
import com.bester.platform.platformchain.util.UserInfoUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liuwen
 * @date 2018/11/3
 */
@RestController
public class PowerRecordController {

    @Resource
    private PowerRecordService powerRecordService;

    @GetMapping("/user/power/valid")
    public CommonResult findPowerRecord(int pageNum, int pageSize) {
        int userId = UserInfoUtil.getUserId();
        if (userId <= 0 || pageNum <= 0 || pageSize <= 0) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        PageInfo<PowerRecordDTO> powerRecordDTOPageInfo = powerRecordService.pageFindUserValidPower(userId, pageNum, pageSize);
        if (powerRecordDTOPageInfo == null) {
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        return CommonResult.success(powerRecordDTOPageInfo);
    }

    @GetMapping("/user/power/expired")
    public CommonResult findExpiredPower(int pageNum, int pageSize) {
        int userId = UserInfoUtil.getUserId();
        if (userId <= 0 || pageNum <= 0 || pageSize <= 0) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        PageInfo<PowerRecordDTO> powerRecordDTOPageInfo = powerRecordService.pageFindUserExpiredPower(userId, pageNum, pageSize);
        if (powerRecordDTOPageInfo == null) {
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        return CommonResult.success(powerRecordDTOPageInfo);
    }

    @GetMapping("/user/power/isValid")
    public CommonResult findValidPower() {
        int userId = UserInfoUtil.getUserId();
        Integer validPowerSum = powerRecordService.findValidPower(userId, 1);
        Integer notValidPowerSum = powerRecordService.findValidPower(userId, 0);
        Map<String, Integer> powerSum = new HashMap<>();
        powerSum.put("validPowerSum", validPowerSum);
        powerSum.put("notValidPowerSum", notValidPowerSum);
        return CommonResult.success(powerSum);
    }

    @GetMapping("/user/power/getPowerBySignIn")
    public CommonResult signIn(){
        Date signInTime = powerRecordService.selectPowerBySource();
        if (signInTime.after(new Date())){

        }
        int userId = UserInfoUtil.getUserId();
        return CommonResult.success();
    }
}
