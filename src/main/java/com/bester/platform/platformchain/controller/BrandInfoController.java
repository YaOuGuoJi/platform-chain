package com.bester.platform.platformchain.controller;

import com.bester.platform.platformchain.common.CommonResult;
import com.bester.platform.platformchain.dto.BrandInfoDTO;
import com.bester.platform.platformchain.dto.UserInfoDTO;
import com.bester.platform.platformchain.enums.HttpStatus;
import com.bester.platform.platformchain.service.BrandInfoService;
import com.bester.platform.platformchain.service.UserInfoService;
import com.bester.platform.platformchain.util.UserInfoUtil;
import lombok.Data;
import org.springframework.util.CollectionUtils;
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
    public CommonResult getBrandInfo(@RequestParam(required = false, defaultValue = "") String brandName,
                                     @RequestParam(required = false, defaultValue = "") Integer type,
                                     @RequestParam(required = false, defaultValue = "") Integer floor) {
        List<BrandInfoDTO> brandInfoDTOList = brandInfoService.selectBrandInfo(brandName, type, floor);
        Map<Integer, List<BrandInfoDTO>> floor2BrandList = brandInfoDTOList.stream().collect(Collectors.groupingBy(BrandInfoDTO::getFloor));
        return CommonResult.success(floor2BrandList);
    }

    /**
     * 根据品牌名称（模糊查询），业态，及楼号查询品牌信息
     *
     * @param brandName
     * @param type
     * @param floor
     * @return
     */
    @GetMapping("/brand/like/collect")
    public CommonResult<List<UserLikeAndCollect>> showLikeAndCollect(@RequestParam(required = false, defaultValue = "") String brandName,
                                                                     @RequestParam(required = false, defaultValue = "") Integer type,
                                                                     @RequestParam(required = false, defaultValue = "") Integer floor) {
        List<BrandInfoDTO> brandInfoDTOList = brandInfoService.selectBrandInfo(brandName, type, floor);
        if (CollectionUtils.isEmpty(brandInfoDTOList)) {
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        List<UserLikeAndCollect> userLikeAndCollectList = judgeLikeAndCollect(brandInfoDTOList);
        return CommonResult.success(userLikeAndCollectList);
    }

    /**
     * 根据点赞数降序展示品牌信息
     *
     * @return
     */
    @GetMapping("/brand/praiseNum")
    public CommonResult<List<UserLikeAndCollect>> getBrandInfoByNum() {
        List<BrandInfoDTO> brandInfoDTOList = brandInfoService.selectByPraiseNum();
        List<UserLikeAndCollect> userLikeAndCollectList = judgeLikeAndCollect(brandInfoDTOList);
        return CommonResult.success(userLikeAndCollectList);
    }

    /**
     * 根据brandId查询某个品牌具体信息
     *
     * @param brandId
     * @return
     */
    @GetMapping("/brand/info")
    public CommonResult getBrandInfoById(Integer brandId) {
        if (brandId < 0) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        BrandInfoDTO brandInfoDTO = brandInfoService.selectBrandById(brandId);
        if (brandInfoDTO == null) {
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
    public CommonResult updateBrandInfoNum(Integer brandId, Integer type) {
        if (brandId == 0 && type == 0) {
            return CommonResult.fail(403, "参数为空");
        }
        String brandIds = String.valueOf(brandId);
        int stepNUM = 1;
        int like = 1;
        int collect = 2;
        int userId = UserInfoUtil.getUserId();
        BrandInfoDTO brandInfoDTO = brandInfoService.selectBrandById(brandId);
        if (brandInfoDTO == null) {
            return CommonResult.fail(404, "暂无该品牌资源");
        }
        int praiseNum = brandInfoDTO.getPraiseNum();
        int collectNum = brandInfoDTO.getCollectNum();
        UserInfoDTO userInfoDTO = userInfoService.selectLikeOrCollect(userId);
        if (type == like) {
            List<String> brandLikeList = userInfoDTO.getBrandLikeList();
            boolean likeFlag = true;
            for (int i = 0; i < brandLikeList.size(); i++) {
                String brandLike = brandLikeList.get(i);
                if (brandLike.equals(brandIds)) {
                    if (praiseNum == 0) {
                        brandInfoService.updateNum(brandId, 0, null);
                    } else {
                        brandInfoService.updateNum(brandId, praiseNum - stepNUM, null);
                    }
                    brandLikeList.remove(i);
                    likeFlag = false;
                    break;
                }
            }
            if (likeFlag) {
                brandInfoService.updateNum(brandId, praiseNum + stepNUM, null);
                brandLikeList.add(brandIds);
            }
            userInfoService.updateLikeOrCollect(userId, brandLikeList, null);
        } else if (type == collect) {
            boolean collectFlag = true;
            List<String> brandCollectList = userInfoDTO.getBrandCollectList();
            for (int i = 0; i < brandCollectList.size(); i++) {
                String brandCollect = brandCollectList.get(i);
                if (brandCollect.equals(brandIds)) {
                    if (collectNum == 0) {
                        brandInfoService.updateNum(brandId, null, 0);
                    } else {
                        brandInfoService.updateNum(brandId, null, collectNum - stepNUM);
                    }
                    brandCollectList.remove(i);
                    collectFlag = false;
                    break;
                }
            }
            if (collectFlag) {
                brandInfoService.updateNum(brandId, null, collectNum + stepNUM);
                brandCollectList.add(brandIds);
            }
            userInfoService.updateLikeOrCollect(userId, null, brandCollectList);
        }
        return CommonResult.success();
    }

    private List<UserLikeAndCollect> judgeLikeAndCollect(List<BrandInfoDTO> brandInfoDTOList) {
        int userId = UserInfoUtil.getUserId();
        UserInfoDTO userInfoDTO = userInfoService.selectLikeOrCollect(userId);
        List<String> likeList = userInfoDTO.getBrandLikeList();
        List<String> collectList = userInfoDTO.getBrandCollectList();
        List<UserLikeAndCollect> userLikeAndCollectList = new ArrayList<>();
        for (BrandInfoDTO brandInfo : brandInfoDTOList) {
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
                } else {
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
    private class UserLikeAndCollect {

        private int userId;

        private int brandId;

        private String brandLogo;

        private String brandName;

        private int floor;

        private int praiseNum;

        private int collectNum;

        private int isLike;

        private int isCollect;

    }
}
