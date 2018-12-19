package com.bester.platform.platformchain.service;

import com.bester.platform.platformchain.dto.BrandInfoDTO;

import java.util.List;

/**
 * @author yanrui
 */
public interface BrandInfoService {

    /**
     * 按条件动态查询所有品牌池
     *
     * @param BrandName
     * @param type
     * @param Floor
     * @return
     */
    List<BrandInfoDTO> selectBrandInfo(String BrandName, String type, Integer Floor);

    /**
     * 根据品牌id查询品牌信息
     *
     * @param BrandId
     * @return
     */
    BrandInfoDTO selectBrandById(Integer BrandId);

    /**
     * 根据点赞数降序排序
     *
     * @return
     */
    List<BrandInfoDTO> selectByPraiseNum();

    /**
     * 修改点赞数与收藏数
     *
     * @param praiseNum
     * @param collectNum
     * @return
     */
    BrandInfoDTO updateNum(Integer praiseNum, Integer collectNum);



}
