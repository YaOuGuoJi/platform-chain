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
