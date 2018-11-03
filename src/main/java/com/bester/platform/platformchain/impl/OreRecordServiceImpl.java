package com.bester.platform.platformchain.impl;

import com.bester.platform.platformchain.dao.OreRecordDao;
import com.bester.platform.platformchain.dto.OreRecordDTO;
import com.bester.platform.platformchain.entity.OreRecordEntity;
import com.bester.platform.platformchain.service.OreRecordService;
import com.bester.platform.platformchain.util.BeansListUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
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
    public List<OreRecordDTO> showOre(OreRecordDTO oreRecordDTO) {
        OreRecordEntity oreRecordEntity = new OreRecordEntity();
        BeanUtils.copyProperties(oreRecordDTO, oreRecordEntity);
        List<OreRecordEntity> oreRecordEntities = oreRecordDao.showOre(oreRecordEntity);
        if (CollectionUtils.isEmpty(oreRecordEntities)) {
            return new ArrayList<>();
        }
        List<OreRecordDTO> oreRecordDTOS = BeansListUtils.copyListProperties(oreRecordEntities, OreRecordDTO.class);
        return oreRecordDTOS;
    }

    @Override
    public Integer receiveOre(OreRecordDTO oreRecordDTO) {
        OreRecordEntity oreRecordEntity = new OreRecordEntity();
        BeanUtils.copyProperties(oreRecordDTO, oreRecordEntity);
        return oreRecordDao.receiveOre(oreRecordEntity);
    }
}
