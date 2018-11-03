package com.bester.platform.platformchain.dao;

import com.bester.platform.platformchain.entity.PowerEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author liuwen
 * @date 2018/11/2
 */
public interface PowerRecordDao {
    // 在这里新增接口

    /**
     * 查询用户有效算力记录
     *
     * @param userId
     * @return
     */
    List<PowerEntity> selectPowerRecordByUserId(@Param("userId") int userId);
}
