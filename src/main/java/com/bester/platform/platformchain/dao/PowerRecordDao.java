package com.bester.platform.platformchain.dao;

import com.bester.platform.platformchain.entity.PowerEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * @author liuwen
 * @date 2018/11/2
 */
public interface PowerRecordDao {

    /**
     * 查询用户有效算力记录
     *
     * @param userId
     * @return
     */
    List<PowerEntity> selectPowerRecordByUserId(@Param("userId") int userId);

    /**
     * 获取用户当前生效的临时算力
     * @param userId 用户ID
     * @param expirationDate 过期日期
     * @return
     */
    int getUserValidTemporaryPower(@Param("userId") Integer userId, @Param("expirationDate")int expirationDate);

    /**
     * 获取用户当前的永久算力
     * @param userId 用户ID
     * @return
     */
    int getUserForeverPower(@Param("userId") Integer userId);

}
