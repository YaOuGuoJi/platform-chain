package com.bester.platform.platformchain.dao;

import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * @author liuwen
 * @date 2018/11/2
 */
public interface OreRecordDao {


    /**
     * 写入用户间隔内生成的矿石
     * @param userId 用户ID
     * @param source 矿石来源
     * @param ore 矿值
     * @param status 矿石状态
     */
    void insertUserOreByInterval(@Param("userId") Integer userId,
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

}
