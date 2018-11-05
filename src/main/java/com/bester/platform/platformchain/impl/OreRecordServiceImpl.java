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
    public List<OreRecordDTO> showOreByUserId(Integer userId) {
        List<OreRecordEntity> oreRecordEntities = oreRecordDao.showOreByUserId(userId);
        if (CollectionUtils.isEmpty(oreRecordEntities)) {
            return new ArrayList<>();
        }
        List<OreRecordDTO> oreRecordDTOS = BeansListUtils.copyListProperties(oreRecordEntities, OreRecordDTO.class);
        return oreRecordDTOS;
    }

    @Override
    public OreRecordDTO showOreById(Integer id) {
        OreRecordEntity oreRecordEntity = oreRecordDao.showOreById(id);
        if(oreRecordEntity==null){
            return null;
        }
        OreRecordDTO oreRecordDTO=new OreRecordDTO();
        BeanUtils.copyProperties(oreRecordEntity,oreRecordDTO);
        return oreRecordDTO;
    }

    @Override
    public Integer receiveOre(Integer id,Integer userId) {
        return oreRecordDao.receiveOre(id,userId);
    }
}
