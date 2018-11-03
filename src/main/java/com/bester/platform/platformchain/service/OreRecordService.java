package com.bester.platform.platformchain.service;

import com.bester.platform.platformchain.dto.OreRecordDTO;

import java.util.List;

/**
 * @author liuwen
 * @date 2018/11/2
 */
public interface OreRecordService {
    /**
     * 根据用户id或用户id以及矿的id查询用户可以领取而未领取的矿
     *
     * @param oreRecordDTO
     * @return
     */
    List<OreRecordDTO> receiveOre(OreRecordDTO oreRecordDTO);
}
