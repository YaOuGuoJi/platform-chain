package com.bester.platform.platformchain.service;

import com.bester.platform.platformchain.dto.PowerRecordDTO;
import com.github.pagehelper.PageInfo;

/**
 * @author liuwen
 * @date 2018/11/2
 */
public interface PowerRecordService {

    /**
     * 分页查询用户算力记录
     *
     * @param userId
     * @param pageSize
     * @param pageNum
     * @return
     */
    PageInfo<PowerRecordDTO> pageFindUserPowerRecord(int userId, int pageSize, int pageNum);
}
