package com.bester.platform.platformchain.dao;

import com.bester.platform.platformchain.entity.PowerEntity;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author liuwen
 * @date 2018/11/2
 */
public interface PowerRecordDao {

    /**
     * 查询用户有效的算力记录
     *
     * @param userId 用户id
     * @param time 有效临时算力的添加时间起始值
     * @return
     */
    List<PowerEntity> selectUserValidPower(@Param("userId") int userId, @Param("time") Date time);

    /**
     * 查询用户已失效算力记录
     *
     * @param userId 用户id
     * @param time 失效临时算力的添加时间截止值
     * @return
     */
    List<PowerEntity> selectUserExpiredPower(@Param("userId") int userId, @Param("time") Date time);
}
