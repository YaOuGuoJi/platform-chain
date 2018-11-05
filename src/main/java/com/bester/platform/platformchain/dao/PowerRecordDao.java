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
     * 修改所有失效的临时算力状态
     *
     * @param time
     * @return
     */
    void updateTemporaryPower(@Param("time") Date time);

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
     * 查找用户有效算力
     *
     * @param userId 用户ID
     * @param valid  是否有效
     * @return
     */
    Integer findValidPower(@Param("userId") Integer userId, @Param("valid") Integer valid);

    /**
     * 查找所有用户的有效算力
     *
     * @param valid 是否有效
     * @return
     */
    Integer findAllUserValidPower(@Param("valid") Integer valid);

    /**
     * 查询所有的用户ID
     *
     * @return
     */
    List<Integer> userIdList();

}
