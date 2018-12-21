package com.bester.platform.platformchain.controller;

import com.bester.platform.platformchain.common.CommonResult;
import com.bester.platform.platformchain.dto.BrandInfoDTO;
import com.bester.platform.platformchain.dto.UserInfoDTO;
import com.bester.platform.platformchain.enums.HttpStatus;
import com.bester.platform.platformchain.service.BrandInfoService;
import com.bester.platform.platformchain.service.UserInfoService;
import com.bester.platform.platformchain.util.UserInfoUtil;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
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

    /**
     * 根据品牌名称（模糊查询），业态，及楼号查询品牌信息
     *
     * @param BrandName
     * @param type
     * @param Floor
     * @return
     */
    @GetMapping("/brand/like/collect")
    public CommonResult<List<UserLikeAndCollect>> showLikeAndCollect(@RequestParam(required = false, defaultValue = "")String BrandName,
                                                                     @RequestParam(required = false, defaultValue = "")String type,
                                                                     @RequestParam(required = false, defaultValue = "")Integer Floor){
        List<BrandInfoDTO> brandInfoDTOList = brandInfoService.selectBrandInfo(BrandName,type,Floor);
        List<UserLikeAndCollect> userLikeAndCollectList = judgeLikeAndCollect(brandInfoDTOList);
        return CommonResult.success(userLikeAndCollectList);
    }

    /**
     * 根据点赞数降序展示品牌信息
     *
     * @return
     */
    @GetMapping("/brand/praiseNum")
    public CommonResult<List<UserLikeAndCollect>> getBrandInfoByNum(){
        List<BrandInfoDTO> brandInfoDTOList = brandInfoService.selectByPraiseNum();
        List<UserLikeAndCollect> userLikeAndCollectList = judgeLikeAndCollect(brandInfoDTOList);
        return CommonResult.success(userLikeAndCollectList);
    }

    /**
     * 根据brandId查询某个品牌具体信息
     *
     * @param BrandId
     * @return
     */
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

    /**
     * 判断是否点赞及收藏并最终给出+1、-1操作
     *
     * @param brandId
     * @param type
     * @return
     */
    @GetMapping("/brand/addNum")
    public CommonResult updateBrandInfoNum(Integer brandId,Integer type){
        if (brandId == 0 && type == 0) {
            return CommonResult.fail(403,"参数为空");
        }
        String brandIds = String.valueOf(brandId);
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
            List<String> brandLikeList = userInfoDTO.getBrandLikeList();
            for (int i = 0; i < brandLikeList.size(); i++) {
                String brandLike = brandLikeList.get(i);
                if (brandLike.equals(brandIds)) {
                    brandInfoService.updateNum(praiseNum - STEP_NUM, null);
                    brandLikeList.remove(i);
                } else {
                    brandInfoService.updateNum(praiseNum + STEP_NUM, null);
                    brandLikeList.add(brandIds);
                }
            }
            userInfoService.updateLikeOrCollect(brandLikeList, null);
        }else if (type == COLLECT) {
            List<String> brandCollectList = userInfoDTO.getBrandCollectList();
            for (int i = 0; i < brandCollectList.size(); i++) {
                String brandCollect = brandCollectList.get(i);
                if (brandCollect.equals(brandIds)) {
                    brandInfoService.updateNum(null, collectNum - STEP_NUM);
                    brandCollectList.remove(i);
                } else {
                    brandInfoService.updateNum(null,collectNum+ STEP_NUM);
                    brandCollectList.add(brandIds);
                }
            }
            userInfoService.updateLikeOrCollect(null, brandCollectList);
        }
        return CommonResult.success();
    }

    private List<UserLikeAndCollect> judgeLikeAndCollect(List<BrandInfoDTO> brandInfoDTOList){
        int userId = UserInfoUtil.getUserId();
        UserInfoDTO userInfoDTO = userInfoService.selectLikeOrCollect(userId);
        List<String> likeList = userInfoDTO.getBrandLikeList();
        List<String> collectList = userInfoDTO.getBrandCollectList();
        List<UserLikeAndCollect> userLikeAndCollectList = new ArrayList<>();
        for (BrandInfoDTO brandInfo: brandInfoDTOList) {
            UserLikeAndCollect userLikeAndCollect = new UserLikeAndCollect();
            userLikeAndCollect.setBrandId(brandInfo.getBrandId());
            userLikeAndCollect.setBrandLogo(brandInfo.getBrandLogo());
            userLikeAndCollect.setBrandName(brandInfo.getBrandName());
            userLikeAndCollect.setFloor(brandInfo.getFloor());
            userLikeAndCollect.setPraiseNum(brandInfo.getPraiseNum());
            userLikeAndCollect.setCollectNum(brandInfo.getCollectNum());
            Integer brandId = brandInfo.getBrandId();
            String brandIds = String.valueOf(brandId);
            for (String like : likeList) {
                if (like.equals(brandIds)) {
                    userLikeAndCollect.setIsLike(1);
                }else {
                    userLikeAndCollect.setIsLike(0);
                }
            }
            for (String collect : collectList) {
                if (collect.equals(brandIds)) {
                    userLikeAndCollect.setIsCollect(1);
                } else {
                    userLikeAndCollect.setIsCollect(0);
                }
            }
            userLikeAndCollectList.add(userLikeAndCollect);
        }
        return userLikeAndCollectList;
    }

    @Data
    private class UserLikeAndCollect{

        private int UserId;

        private int brandId;

        private String brandLogo;

        private String brandName;

        private int Floor;

        private int praiseNum;

        private int collectNum;

        private int isLike;

        private int isCollect;

    }
}
