package com.bester.platform.platformchain.controller;

import com.bester.platform.platformchain.common.CommonResult;
import com.bester.platform.platformchain.dao.UserInfoDao;
import com.bester.platform.platformchain.dto.BrandInfoDTO;
import com.bester.platform.platformchain.dto.UserInfoDTO;
import com.bester.platform.platformchain.enums.HttpStatus;
import com.bester.platform.platformchain.service.BrandInfoService;
import com.bester.platform.platformchain.service.UserInfoService;
import com.bester.platform.platformchain.util.UserInfoUtil;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
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
    @Resource
    private UserInfoService userInfoService;

    @GetMapping("/brand/list")
    public CommonResult getBrandInfo(@RequestParam(required = false, defaultValue = "")String BrandName,
                                     @RequestParam(required = false, defaultValue = "")String type,
                                     @RequestParam(required = false, defaultValue = "")Integer Floor){
        List<BrandInfoDTO> brandInfoDTOList = brandInfoService.selectBrandInfo(BrandName,type,Floor);
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

    @GetMapping("/brand/praiseNum")
    public CommonResult getBrandInfoByNum(){
        List<BrandInfoDTO> brandInfoDTOList = brandInfoService.selectByPraiseNum();
        if (brandInfoDTOList.isEmpty()){
            return CommonResult.fail(404,"暂无数据");
        }
        return CommonResult.success(brandInfoDTOList);
    }

    @GetMapping("/brand/addNum")
    public CommonResult updateBrandInfoNum(Integer brandId,Integer type){
        if (brandId == 0 && type == 0) {
            return CommonResult.fail(403,"参数为空");
        }
        int STEP_NUM = 1;
        int LIKE = 1;
        int COLLECT = 2;
        int userId = UserInfoUtil.getUserId();
        BrandInfoDTO brandInfoDTO = brandInfoService.selectBrandById(brandId);
        if (brandInfoDTO == null) {
            return CommonResult.fail(404,"暂无该品牌资源");
        }
        int praiseNum = brandInfoDTO.getPraiseNum();
        int collectNum = brandInfoDTO.getCollectNum();
        UserInfoDTO userInfoDTO = userInfoService.selectLikeOrCollect(userId);
        if (type == LIKE) {
            List<Integer> brandLikeList = userInfoDTO.getBrandCollectList();
            for (int brandLike = 0; brandLike <= brandLikeList.size(); brandLike++) {
                if (brandId == brandLike) {
                    brandInfoService.updateNum(praiseNum - STEP_NUM, null);
                    brandLikeList.remove(brandLike);
                } else {
                    brandInfoService.updateNum(praiseNum + STEP_NUM, null);
                    brandLikeList.add(brandId);
                }
            }
            userInfoService.updateLikeOrCollect(brandLikeList, null);
        }else if (type == COLLECT) {
            List<Integer> brandCollectList = userInfoDTO.getBrandCollectList();
            for (int brandCollect = 0; brandCollect <= brandCollectList.size(); brandCollect++) {
                if (brandCollect == brandId) {
                    brandInfoService.updateNum(null, collectNum - STEP_NUM);
                    brandCollectList.remove(brandCollect);
                }else {
                    brandInfoService.updateNum(null,collectNum+ STEP_NUM);
                    brandCollectList.add(brandId);
                }
            }
            userInfoService.updateLikeOrCollect(null, brandCollectList);
        }
        return CommonResult.success();
    }

    @GetMapping("/brand/like/collect")
    public CommonResult showLikeAndCollect(){
        int userId = UserInfoUtil.getUserId();
        UserInfoDTO userInfoDTO = userInfoService.selectLikeOrCollect(userId);
        Map<String,List<Integer>> resultMap = new HashMap<>();
        List<Integer> likeList = userInfoDTO.getBrandLikeList();
        List<Integer> collectList = userInfoDTO.getBrandCollectList();
        resultMap.put("likeList",likeList);
        resultMap.put("collectList",collectList);
        return CommonResult.success(resultMap);
    }
}
