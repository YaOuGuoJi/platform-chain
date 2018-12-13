package com.bester.platform.platformchain.controller;

import com.bester.platform.platformchain.common.CommonResult;
import com.bester.platform.platformchain.dto.BrandInfoDTO;
import com.bester.platform.platformchain.enums.HttpStatus;
import com.bester.platform.platformchain.service.BrandInfoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author yanrui
 */
@RestController
public class BrandInfoController {

    @Resource
    private BrandInfoService brandInfoService;

    @GetMapping("/brand/list")
    public CommonResult getBrandInfo(@RequestParam(required = false, defaultValue = "")String BrandName){
        List<BrandInfoDTO> brandInfoDTOList = brandInfoService.selectBrandInfo(BrandName);
        Map<Integer,List<BrandInfoDTO>> floor2BrandList = brandInfoDTOList.stream().collect(Collectors.groupingBy(BrandInfoDTO::getFloor));
        return CommonResult.success(floor2BrandList);
    }

    @GetMapping("/brand/info")
    public CommonResult getBrandInfoById(Integer BrandId){
        if (BrandId == null){
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        BrandInfoDTO brandInfoDTO = brandInfoService.selectBrandById(BrandId);
        if (brandInfoDTO == null){
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        return CommonResult.success(brandInfoDTO);
    }
}
