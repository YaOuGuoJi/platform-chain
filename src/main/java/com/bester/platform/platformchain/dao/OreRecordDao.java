package com.bester.platform.platformchain.dao;

import com.bester.platform.platformchain.entity.OreRecordEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author liuwen
 * @date 2018/11/2
 */
public interface OreRecordDao {
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
