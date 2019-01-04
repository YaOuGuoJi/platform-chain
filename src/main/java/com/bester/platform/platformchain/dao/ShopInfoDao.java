package com.bester.platform.platformchain.dao;

import com.bester.platform.platformchain.entity.ShopInfoEntity;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * @author liuwen
 * @date 2018/12/29
 */
public interface ShopInfoDao {

    /**
     * 批量查询
     *
     * @param shopIds
     * @return
     */
    List<ShopInfoEntity> batchSelect(@Param("shopIds") Collection<Integer> shopIds);
}
