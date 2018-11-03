package com.bester.platform.platformchain.dao;

import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * @author liuwen
 * @date 2018/11/2
 */
public interface OreRecordDao {
    // 在这里新增接口

    /**
     * 修改过期的待领取矿石状态
     *
     * @param time
     * @return
     */
    int updateOverduePower(@Param("time") Date time);
}
