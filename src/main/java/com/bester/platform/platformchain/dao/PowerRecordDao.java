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
     * 修改所有失效的临时算力状态
     *
     * @param time
     * @return
     */
    int updateTemporaryPower(@Param("time") Date time);

    /**
     * 查询用户有效的算力记录
     *
     * @param userId 用户id
     * @param time   有效临时算力的添加时间起始值
     * @return
     */
    List<PowerEntity> selectUserValidPower(@Param("userId") int userId, @Param("time") Date time);

    /**
     * 查询用户已失效算力记录
     *
     * @param userId 用户id
     * @param time   失效临时算力的添加时间截止值
     * @return
     */
    List<PowerEntity> selectUserExpiredPower(@Param("userId") int userId, @Param("time") Date time);

    /**
     * 获取用户当前生效的临时算力
     *
     * @param userId         用户ID
     * @param expirationDate 过期日期
     * @return
     */
    Integer getUserValidTemporaryPower(@Param("userId") Integer userId, @Param("expirationDate") Date expirationDate);

    /**
     * 获取用户当前的永久算力
     *
     * @param userId 用户ID
     * @return
     */
    Integer getUserForeverPower(@Param("userId") Integer userId);

    /**
     * 查询所有的用户ID
     * @return
     */
    List<Integer> userIdList();

}
