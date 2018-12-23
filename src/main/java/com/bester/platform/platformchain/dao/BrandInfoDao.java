package com.bester.platform.platformchain.dao;

import com.bester.platform.platformchain.entity.BrandInfoEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author yanrui
 */
public interface BrandInfoDao {

    /**
     * 按条件动态查询所有品牌池
     *
     * @param brandName
     * @param type
     * @param floor
     * @return
     */
    List<BrandInfoEntity> selectBrandInfo(@Param("brandName") String brandName, @Param("type") Integer type, @Param("floor") Integer floor);

    /**
     * 根据品牌id查询品牌
     *
     * @param brandId
     * @return
     */
    BrandInfoEntity selectBrandById(@Param("brandId")int brandId);

    /**
     * 根据点赞数降序查询品牌
     *
     * @return
     */
    List<BrandInfoEntity> selectByPraiseNum();

    /**
     * 修改点赞数与收藏数
     *
     * @param brandId
     * @param praiseNum
     * @param collectNum
     * @return
     */
    int updateNum(@Param("brandId")Integer brandId,
                              @Param("praiseNum")Integer praiseNum,
                              @Param("collectNum")Integer collectNum);
}
