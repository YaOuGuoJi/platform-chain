package com.bester.platform.platformchain.dao;

import com.bester.platform.platformchain.entity.OreRecordEntity;

import java.util.Date;
import java.util.List;

/**
 * @author liuwen
 * @date 2018/11/2
 */
public interface OreRecordDao {
    // 在这里新增接口

    /**
     * 查询所有状态为待领取的矿石
     * @return
     */
    List<OreRecordEntity> selectOverduePower();

    /**
     * 修改过期的待领取矿石状态
     * @return
     */
    int updateOverduePower();
}
