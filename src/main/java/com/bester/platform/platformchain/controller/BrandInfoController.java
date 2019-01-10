package com.bester.platform.platformchain.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bester.platform.platformchain.common.CommonResult;
import com.bester.platform.platformchain.enums.HttpStatus;
import com.bester.platform.platformchain.util.UserInfoUtil;
import com.google.common.collect.Lists;
import com.xianbester.api.constant.BrandActionType;
import com.xianbester.api.dto.BrandInfoDTO;
import com.xianbester.api.dto.UserInfoDTO;
import com.xianbester.api.service.BrandInfoService;
import com.xianbester.api.service.UserInfoService;
import lombok.Data;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author yanrui
 */
@RestController
public class BrandInfoController {

    @Reference
    private BrandInfoService brandInfoService;
    @Reference
    private UserInfoService userInfoService;

    /**
     * 品牌列表查询
     *
     * @param brandName 模糊匹配品牌名称
     * @param brandType 品牌类型
     * @param floor     楼层
     * @param orderType 排序类型
     * @return
     * @see com.xianbester.api.constant.BrandActionType
     */
    @GetMapping("/brand/list")
    public CommonResult<List<BrandVO>> brandList(@RequestParam(required = false, defaultValue = "") String brandName,
                                                 @RequestParam(required = false, defaultValue = "0") Integer brandType,
                                                 @RequestParam(required = false, defaultValue = "0") Integer floor,
                                                 @RequestParam(required = false, defaultValue = "0") Integer orderType) {
        List<BrandInfoDTO> brandInfoDTOList = brandInfoService.selectBrandInfo(brandName, brandType, floor);
        if (CollectionUtils.isEmpty(brandInfoDTOList)) {
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        List<BrandVO> brandVOList = judgeBrandVOList(brandInfoDTOList);
        if (orderType == BrandActionType.PRAISE) {
            brandVOList.sort(Comparator.comparing(BrandVO::getPraiseNum).reversed());
        } else if (orderType == BrandActionType.COLLECT) {
            brandVOList.sort(Comparator.comparing(BrandVO::getCollectNum).reversed());
        }
        return CommonResult.success(brandVOList);
    }

    /**
     * 根据brandId查询某个品牌具体信息
     *
     * @param brandId
     * @return
     */
    @GetMapping("/brand/detail")
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
     * （取消）点赞/收藏
     *
     * @param brandId
     * @param type
     * @return
     * @see com.xianbester.api.constant.BrandActionType
     */
    @GetMapping("/brand/addNum")
    public CommonResult updateBrandInfoNum(Integer brandId, Integer type) {
        if (brandId <= 0 || type <= 0) {
            return CommonResult.fail(403, "参数错误");
        }
        String brandIdStr = String.valueOf(brandId);
        BrandInfoDTO brandInfoDTO = brandInfoService.selectBrandById(brandId);
        if (brandInfoDTO == null) {
            return CommonResult.fail(404, "暂无该品牌资源");
        }
        int userId = UserInfoUtil.getUserId();
        UserInfoDTO userInfoDTO = userInfoService.findUserInfoByUserId(userId);
        int number = type == BrandActionType.PRAISE ? brandInfoDTO.getPraiseNum() : brandInfoDTO.getCollectNum();
        String brands = type == BrandActionType.PRAISE ? userInfoDTO.getBrandLikeList() : userInfoDTO.getBrandCollectList();
        List<String> brandIdList = this.split(brands);
        if (brandIdList.contains(brandIdStr)) {
            brandIdList.remove(brandIdStr);
            number--;
        } else {
            brandIdList.add(brandIdStr);
            number++;
        }
        brandInfoService.updatePraiseOrCollectNum(brandId, type, number);
        userInfoService.updateLikeOrCollect(userId, type, String.join(",", brandIdList));
        return CommonResult.success();
    }

    /**
     * 根据brandIdList查询brandInfoList
     *
     * @param brandIdList
     * @return
     */
    @GetMapping("/brand/idList")
    public CommonResult selectByIdList(List<Integer> brandIdList) {
        if (CollectionUtils.isEmpty(brandIdList)) {
            return CommonResult.fail(403,"参数错误");
        }
        List<BrandInfoDTO> brandInfoDTOList = brandInfoService.selectByIdList(brandIdList);
        if (CollectionUtils.isEmpty(brandInfoDTOList)) {
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        return CommonResult.success(brandInfoDTOList);
    }

    private List<BrandVO> judgeBrandVOList(List<BrandInfoDTO> brandInfoDTOList) {
        int userId = UserInfoUtil.getUserId();
        UserInfoDTO userInfoDTO = userInfoService.findUserInfoByUserId(userId);
        List<String> likeList = this.split(userInfoDTO.getBrandLikeList());
        List<String> collectList = this.split(userInfoDTO.getBrandCollectList());
        List<BrandVO> brandVOList = new ArrayList<>();
        for (BrandInfoDTO brandInfo : brandInfoDTOList) {
            BrandVO brandVO = new BrandVO();
            brandVO.setBrandId(brandInfo.getBrandId());
            brandVO.setBrandLogo(brandInfo.getBrandLogo());
            brandVO.setBrandName(brandInfo.getBrandName());
            brandVO.setFloor(brandInfo.getFloor());
            brandVO.setPraiseNum(brandInfo.getPraiseNum());
            brandVO.setCollectNum(brandInfo.getCollectNum());
            brandVO.setLike(likeList.contains(brandInfo.getBrandId() + ""));
            brandVO.setCollect(collectList.contains(brandInfo.getBrandId() + ""));
            brandVOList.add(brandVO);
        }
        return brandVOList;
    }

    private List<String> split(String listStr) {
        if (StringUtils.isEmpty(listStr)) {
            return Lists.newArrayList();
        }
        return Lists.newArrayList(listStr.split(","));
    }

    @Data
    private class BrandVO {

        private Integer brandId;

        private String brandLogo;

        private String brandName;

        private Integer floor;

        private Integer praiseNum;

        private Integer collectNum;

        private Boolean like;

        private Boolean collect;

    }
}
