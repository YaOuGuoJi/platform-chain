package com.bester.platform.platformchain.dao;

import com.bester.platform.platformchain.entity.OreRecordEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author liuwen
 * @date 2018/11/2
 */
public interface OreRecordDao {
    // 在这里新增接口

    /**
     * 根据用户id或用户id以及矿的id查询用户可以领取而未领取的矿
     * @param oreRecordEntity
     * @return
     */
    List<OreRecordEntity> receiveOre(@Param("oreRecordEntity") OreRecordEntity oreRecordEntity);
}
