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
     * @param brandName
     * @param type
     * @param floor
     * @return
     */
    List<BrandInfoDTO> selectBrandInfo(String brandName, Integer type, Integer floor);

    /**
     * 根据品牌id查询品牌信息
     *
     * @param brandId
     * @return
     */
    BrandInfoDTO selectBrandById(Integer brandId);

    /**
     * 根据点赞数降序排序
     *
     * @return
     */
    List<BrandInfoDTO> selectByPraiseNum();

    /**
     * 修改点赞数与收藏数
     *
     * @param brandId
     * @param praiseNum
     * @param collectNum
     * @return
     */
    int updateNum(Integer brandId,Integer praiseNum, Integer collectNum);



}
