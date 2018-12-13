package com.bester.platform.platformchain.service;

import com.bester.platform.platformchain.dto.BrandInfoDTO;

import java.util.List;

/**
 * @author yanrui
 */
public interface BrandInfoService {

    /**
     * 查询所有品牌池
     *
     * @param BrandName
     * @return
     */
    List<BrandInfoDTO> selectBrandInfo(String BrandName);

    /**
     * 根据品牌id查询品牌信息
     *
     * @param BrandId
     * @return
     */
    BrandInfoDTO selectBrandById(Integer BrandId);
}
