package com.bester.platform.platformchain.controller;

import com.bester.platform.platformchain.common.CommonResult;
import com.bester.platform.platformchain.constant.OreRecordStatus;
import com.bester.platform.platformchain.dto.OreRecordDTO;
import com.bester.platform.platformchain.service.OreRecordService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author liangqimin
 * @date 2018/11/3
 */
@RestController
public class UserReceiveOreController {
    @Resource
    private OreRecordService oreRecordService;

    @GetMapping("user/unreceived/ore")
    public CommonResult showOre(Integer userId) {
        List<OreRecordDTO> oreRecordDTOs = oreRecordService.showOreByUserId(userId);
        return CommonResult.success(oreRecordDTOs);
    }

    @GetMapping("user/received/ore")
    public CommonResult receiveOre(Integer userId, Integer oreId) {
        OreRecordDTO oreRecordDTO = oreRecordService.showOreById(oreId);
        if (oreRecordDTO == null) {
            return CommonResult.fail(404, "没有这条记录");
        }
        if (!oreRecordDTO.getUserId().equals(userId)) {
            return CommonResult.fail(403, "参数错误");
        }
        if (oreRecordDTO.getStatus() == OreRecordStatus.RECEIVED) {
            return CommonResult.fail(403, "已操作");
        }
        if (oreRecordDTO.getStatus() == OreRecordStatus.DEPRECATED) {
            return CommonResult.fail(403, "已过期");
        }
        Integer sta = oreRecordService.receiveOre(oreId);
        if (sta == 0) {
            return CommonResult.fail(403, "操作失败");
        }
        return CommonResult.success();
    }
}
