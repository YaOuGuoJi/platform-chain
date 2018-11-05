package com.bester.platform.platformchain.controller;

import com.bester.platform.platformchain.common.CommonResult;
import com.bester.platform.platformchain.common.CommonResultBuilder;
import com.bester.platform.platformchain.dto.OreRecordDTO;
import com.bester.platform.platformchain.enums.HttpStatus;
import com.bester.platform.platformchain.service.OreRecordService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;

@RestController
public class OreRecordController {
    @Resource
    private OreRecordService oreRecordService;

    /**
     * 矿石纪录查询
     *
     * @param userId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/user/oreRecord")
    public CommonResult queryAllOreReocrdByUserId(Integer userId, int pageNum, int pageSize) {
        if (userId <= 0 || pageNum <= 0 || pageSize <= 0) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        PageInfo<OreRecordDTO> oreRecordDTOPageInfo = oreRecordService.queryOreRecordByUserId(userId, pageNum, pageSize);
        if (oreRecordDTOPageInfo == null) {
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        return new CommonResultBuilder()
                .code(200)
                .message("查询成功")
                .data("矿石来源查询", oreRecordDTOPageInfo)
                .build();
    }

    /**
     * 矿量查询
     *
     * @param userId
     * @return
     */
    @GetMapping("/user/oreNumber")
    public CommonResult queryOreNumbByUserId(Integer userId) {
        if (userId <= 0) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        BigDecimal bigDecimal = oreRecordService.queryOreNumbByUserId(userId);
        if (bigDecimal.compareTo(BigDecimal.ZERO) == 0){
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
            return new CommonResultBuilder()
                    .code(200)
                    .message("查询成功")
                    .data("矿石数量查询", bigDecimal)
                    .build();
    }
}
