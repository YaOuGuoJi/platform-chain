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
    public PageInfo<PowerRecordDTO> pageFindUserValidPower(int userId, int pageNum, int pageSize) {
        Assert.isTrue(userId > 0 && pageSize > 0 && pageNum > 0, "参数错误");
        PageHelper.startPage(pageNum, pageSize, true);
        List<PowerEntity> powerEntities = powerRecordDao.selectUserValidPower(userId);
        return BeansListUtils.copyListPageInfo(powerEntities, PowerRecordDTO.class);
    }

    @Override
    public PageInfo<PowerRecordDTO> pageFindUserExpiredPower(int userId, int pageNum, int pageSize) {
        Assert.isTrue(userId > 0 && pageSize > 0 && pageNum > 0, "参数错误");
        PageHelper.startPage(pageNum, pageSize, true);
        List<PowerEntity> powerEntities = powerRecordDao.selectUserExpiredPower(userId);
        return BeansListUtils.copyListPageInfo(powerEntities, PowerRecordDTO.class);
    }
}
