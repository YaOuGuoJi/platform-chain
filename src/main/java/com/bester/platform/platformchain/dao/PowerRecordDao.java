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
     * 修改所有失效的临时算力状态
     *
     * @param time
     * @return
     */
    void updateTemporaryPower(@Param("time") Date time);

    /**
     * 查询用户算力记录
     *
     * @param userId
     * @return
     */
    List<PowerEntity> selectUserValidPower(@Param("userId") int userId);

    /**
     * 查询用户已失效算力记录
     *
     * @param userId
     * @return
     */
    List<PowerEntity> selectUserExpiredPower(@Param("userId") int userId);

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

    /**
     * 给用户添加算力
     *
     * @param powerEntity 算力实体
     * @return
     */
    int addUserPower(@Param("powerEntity") PowerEntity powerEntity);


    /**
     * @param userId
     * @return
     */
    Date selectPowerBySource(@Param("userId") int userId);

}
