package com.bester.platform.platformchain.service;

import com.bester.platform.platformchain.dto.OreRecordDTO;

import java.util.List;

/**
 * @author liuwen
 * @date 2018/11/2
 */
public interface OreRecordService {
    /**
     * 根据用户id查询用户可以领取而未领取的矿
     *
     * @param userId
     * @return
     */
    List<OreRecordDTO> showOreByUserId(Integer userId);

    /**
     * 根据矿的id查矿
     *
     * @param id
     * @return
     */
    OreRecordDTO showOreById(Integer id);

    /**
     * 根据矿的id把状态为2的矿修改为1(收矿)
     *
     * @param id
     * @param userId
     * @return
     */
    Integer receiveOre(Integer id, Integer userId);
}
