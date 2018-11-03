package com.bester.platform.platformchain.impl;

import com.bester.platform.platformchain.dao.OreRecordDao;
import com.bester.platform.platformchain.dto.OreRecordDTO;
import com.bester.platform.platformchain.entity.OreRecordEntity;
import com.bester.platform.platformchain.service.OreRecordService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

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
    public List<OreRecordDTO> receiveOre(OreRecordDTO oreRecordDTO) {
        OreRecordEntity oreRecordEntity=new OreRecordEntity();
        BeanUtils.copyProperties(oreRecordDTO,oreRecordEntity);
        List<OreRecordEntity> oreRecordEntities = oreRecordDao.receiveOre(oreRecordEntity);
        List<OreRecordDTO> oreRecordDTOs=new ArrayList<>();
        for(OreRecordEntity oreRecordEntity2:oreRecordEntities){
            OreRecordDTO oreRecordDTO2=new OreRecordDTO();
            BeanUtils.copyProperties(oreRecordEntity2,oreRecordDTO);
            oreRecordDTOs.add(oreRecordDTO2);
        }
        return oreRecordDTOs;
    }
}
