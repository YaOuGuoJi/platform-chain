package com.bester.platform.platformchain.service;

import com.bester.platform.platformchain.dto.OreRecordDTO;
import com.github.pagehelper.PageInfo;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author liuwen
 * @date 2018/11/2
 */
public interface OreRecordService {
    /**
     * 矿量查询
     *
     * @param userId
     * @return
     */
    BigDecimal queryOreNumbByUserId(Integer userId);

    /**
     * 矿石纪录分页查询
     *
     * @param userId
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<OreRecordDTO> queryOreRecordByUserId(Integer userId, int pageNum, int pageSize);

    /**
     * 根据矿的id把状态为2的矿修改为1(收矿)
     *
     * @param id
     * @return
     */
    Integer receiveOre(Integer id);

    /**
     * 根据矿的id查矿
     *
     * @param id
     * @return
     */
    OreRecordDTO showOreById(Integer id);

    /**
     * 根据用户id查询用户可以领取而未领取的矿
     *
     * @param userId
     * @return
     */
    List<OreRecordDTO> showOreByUserId(Integer userId);
}
