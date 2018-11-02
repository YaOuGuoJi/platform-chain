package com.bester.platform.platformchain.dao;

/**
 * @author liuwen
 * @date 2018/11/2
 */
public interface PowerRecordDao {
    // 在这里新增接口

    /**
     * 获得所有永生算力
     * @return
     */
    int selectForeverPower();

    /**
     * 获得所有临时算力
     * @return
     */
    int selectTemporaryPower();
}
