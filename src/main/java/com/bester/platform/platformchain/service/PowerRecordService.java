package com.bester.platform.platformchain.service;

import com.bester.platform.platformchain.dto.PowerRecordDTO;
import com.github.pagehelper.PageInfo;

/**
 * @author liuwen
 * @date 2018/11/2
 */
public interface PowerRecordService {

    /**
     * 分页查询用户有效算力记录
     *
     * @param userId
     * @param pageSize
     * @param pageNum
     * @return
     */
    PageInfo<PowerRecordDTO> pageFindUserValidPower(int userId, int pageNum, int pageSize);


    /**
     * 分页查询用户失效算力记录
     *
     * @param userId
     * @param pageSize
     * @param pageNum
     * @return
     */
    PageInfo<PowerRecordDTO> pageFindUserExpiredPower(int userId, int pageNum, int pageSize);

    /**
     * 查询用户生效与未生效原力值
     *
     * @param userId
     * @param valid
     * @return
     */
    Integer findValidPower(int userId, int valid);

}
