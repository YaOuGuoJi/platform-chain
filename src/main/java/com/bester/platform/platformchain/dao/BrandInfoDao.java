package com.bester.platform.platformchain.dao;

import com.bester.platform.platformchain.entity.BrandInfoEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author yanrui
 */
public interface BrandInfoDao {

    /**
     * 按条件动态查询所有品牌池
     *
     * @param BrandName
     * @param type
     * @param Floor
     * @return
     */
    List<BrandInfoEntity> selectBrandInfo(@Param("BrandName") String BrandName, @Param("type") String type, @Param("Floor") Integer Floor);

    /**
     * 根据品牌id查询品牌
     *
     * @param BrandId
     * @return
     */
    BrandInfoEntity selectBrandById(@Param("BrandId")int BrandId);

    /**
     * 根据点赞数降序查询品牌
     *
     * @return
     */
    List<BrandInfoEntity> selectByPraiseNum();

    /**
     * 修改点赞数与收藏数
     *
     * @param praiseNum
     * @param collectNum
     * @return
     */
    BrandInfoEntity updateNum(@Param("PraiseNum")Integer praiseNum,@Param("collectNum")Integer collectNum);
}
