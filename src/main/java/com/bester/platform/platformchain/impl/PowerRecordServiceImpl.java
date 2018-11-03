package com.bester.platform.platformchain.impl;

import com.bester.platform.platformchain.dao.PowerRecordDao;
import com.bester.platform.platformchain.dto.PowerRecordDTO;
import com.bester.platform.platformchain.entity.PowerEntity;
import com.bester.platform.platformchain.service.PowerRecordService;
import com.bester.platform.platformchain.util.BeansListUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author liuwen
 * @date 2018/11/2
 */
@Service
public class PowerRecordServiceImpl implements PowerRecordService {

    @Resource
    private PowerRecordDao powerRecordDao;

    @Override
    public PageInfo<PowerRecordDTO> pageFindUserPowerRecord(int userId, int pageSize, int pageNum) {
        Assert.isTrue(userId > 0 && pageSize > 0 && pageNum > 0, "参数错误");
        PageHelper.startPage(pageSize, pageNum, true);
        List<PowerEntity> powerEntities = powerRecordDao.selectPowerRecordByUserId(userId);
        return BeansListUtils.copyListPageInfo(powerEntities, PowerRecordDTO.class);
    }
}
