package com.bester.platform.platformchain.dao;

import com.bester.platform.platformchain.entity.BrandInfoEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author yanrui
 */
public interface BrandInfoDao {

    /**
     * 查询所有品牌池
     *
     * @param BrandName
     * @return
     */
    List<BrandInfoEntity> selectBrandInfo(@Param("BrandName") String BrandName);

    BrandInfoEntity selectBrandById(@Param("BrandId")int BrandId);
}
