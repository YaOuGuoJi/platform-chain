package com.bester.platform.platformchain.controller;

import com.bester.platform.platformchain.common.CommonResult;
import com.bester.platform.platformchain.common.CommonResultBuilder;
import com.bester.platform.platformchain.dto.OreRecordDTO;
import com.bester.platform.platformchain.enums.HttpStatus;
import com.bester.platform.platformchain.service.OreProduceService;
import com.bester.platform.platformchain.service.OreRecordService;
import com.bester.platform.platformchain.util.UserInfoUtil;
import com.github.pagehelper.PageInfo;
import org.joda.time.DateTime;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;

@RestController
public class OreRecordController {

    @Resource
    private OreRecordService oreRecordService;
    @Resource
    private OreProduceService oreProduceService;

    /**
     * 矿石纪录查询
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/user/oreRecord")
    public CommonResult queryAllOreRecordByUserId(int pageNum, int pageSize) {
        int userId = UserInfoUtil.getUserId();
        if (userId <= 0 || pageNum <= 0 || pageSize <= 0) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        PageInfo<OreRecordDTO> oreRecordDTOPageInfo = oreRecordService.queryOreRecordByUserId(userId, pageNum, pageSize);
        if (oreRecordDTOPageInfo == null) {
            return CommonResult.fail(404,"没记录了");
        }
        return new CommonResultBuilder()
                .code(200)
                .message("查询成功")
                .data("oreRecordDTOPageInfo", oreRecordDTOPageInfo)
                .build();
    }

    /**
     * 矿量查询
     *
     * @return
     */
    @GetMapping("/user/oreNumber")
    public CommonResult queryOreNumbByUserId() {
        int userId = UserInfoUtil.getUserId();
        if (userId <= 0) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        BigDecimal bigDecimal = oreRecordService.queryOreNumbByUserId(userId);
        if (bigDecimal == null || bigDecimal.compareTo(BigDecimal.ZERO) == 0){
            bigDecimal = new BigDecimal("0");
        }
        return new CommonResultBuilder()
                .code(200)
                .message("查询成功")
                .data("oreNumber", bigDecimal)
                .build();
    }

    /**
     * 查询累计矿石总量和昨日矿石总产量
     *
     * @return
     */
    @GetMapping("/ore/total")
    public CommonResult queryTotalOre() {
        BigDecimal total = oreProduceService.totalOreNumber();
        BigDecimal day = oreProduceService.oreNumberByDay(new DateTime().getYear());
        return new CommonResultBuilder()
                .code(200).message("查询成功").data("total", total).data("day", day).build();
    }
}
