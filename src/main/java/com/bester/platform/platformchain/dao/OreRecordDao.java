package com.bester.platform.platformchain.dao;

import com.bester.platform.platformchain.entity.OreRecordEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * @author liuwen
 * @date 2018/11/2
 */
public interface OreRecordDao {


    /**
     * 写入用户每小时生成的矿石
     * @param userId 用户ID
     * @param source 矿石来源
     * @param ore 矿值
     * @param status 矿石状态
     */
    void insertUserOreByHour(@Param("userId") Integer userId,
                             @Param("source") String source,
                             @Param("ore")BigDecimal ore,
                             @Param("status") Integer status);

    /**
     * 查找用户当天生成的矿值的个数
     * @param status 矿石状态
     * @param userId 用户ID
     * @return
     */
    Integer findGrowingOreByEveryday(@Param("userId") Integer userId, @Param("status") Integer status);

    // 在这里新增接口

    /**
     * 根据用户id查询用户可以领取而未领取的矿
     *
     * @param userId
     * @return
     */
    List<OreRecordEntity> showOreByUserId(@Param("userId") Integer userId);

    /**
     * 根据矿id查矿记录
     *
     * @param id
     * @return
     */
    OreRecordEntity showOreById(@Param("id") Integer id);

    /**
     * 根据矿的id把状态为2的矿修改为1(收矿)
     * @param id
     * @param userId
     * @return
     */

    Integer receiveOre(@Param("id") Integer id,
                       @Param("userId") Integer userId);


}
