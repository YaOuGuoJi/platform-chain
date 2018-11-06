package com.bester.platform.platformchain.impl;

import com.bester.platform.platformchain.constant.OreRecordStatus;
import com.bester.platform.platformchain.dao.OreRecordDao;
import com.bester.platform.platformchain.dto.OreRecordDTO;
import com.bester.platform.platformchain.entity.OreRecordEntity;
import com.bester.platform.platformchain.service.OreRecordService;
import com.bester.platform.platformchain.util.BeansListUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author liuwen
 * @date 2018/11/2
 */
@Service
public class OreRecordServiceImpl implements OreRecordService {
    @Resource
    private OreRecordDao oreRecordDao;

    @Override
    public BigDecimal queryOreNumbByUserId(Integer userId) {
        BigDecimal oreNumb = oreRecordDao.queryOreNumbByUserId(userId);
        return oreNumb;
    }

    @Override
    public PageInfo<OreRecordDTO> queryOreRecordByUserId(Integer userId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        int sta = OreRecordStatus.RECEIVED;
        List<OreRecordEntity> oreRecordEntities = oreRecordDao.queryAllOreRecordByUserId(userId, sta);
        return BeansListUtils.copyListPageInfo(oreRecordEntities, OreRecordDTO.class);
    }
}
