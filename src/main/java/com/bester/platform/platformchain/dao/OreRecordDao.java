package com.bester.platform.platformchain.dao;

import com.bester.platform.platformchain.entity.OreRecordEntity;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author liuwen
 * @date 2018/11/2
 */
public interface OreRecordDao {
    // 在这里新增接口

    /**
     * 查询所有用户的最新领取记录
     *
     * @return
     */
    List<OreRecordEntity> selectMaxUpdateTime();

    /**
     * 修改所有过期矿石状态
     *
     * @param userId 用户Id
     * @return
     */
    int updateOverduePower(@Param("userId")Integer userId);

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

}
