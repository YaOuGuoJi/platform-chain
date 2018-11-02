package com.bester.platform.platformchain.dao;

/**
 * @author liuwen
 * @date 2018/11/2
 */
public interface TotalPowerDao {
    // 在这里新增接口

    /**
     * 添加当天所有用户总算力
     * @param totalPower
     * @return
     */
    int insertTotalPower(int totalPower);
}
