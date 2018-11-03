package com.bester.platform.platformchain.dao;


import org.apache.ibatis.annotations.Param;

import java.util.Date;

import com.bester.platform.platformchain.entity.PowerEntity;

import java.util.List;


/**
 * @author liuwen
 * @date 2018/11/2
 */
public interface PowerRecordDao {
    // 在这里新增接口

    /**
     * 获得所有永生算力
     *
     * @return
     */
    int selectForeverPower();

    /**
     * 获得所有临时算力
     *
     * @param time
     * @return
     */
    int selectTemporaryPower(@Param("time") Date time);

    /**
     * 查询用户有效算力记录
     *
     * @param userId
     * @return
     */
    List<PowerEntity> selectPowerRecordByUserId(@Param("userId") int userId);

}
