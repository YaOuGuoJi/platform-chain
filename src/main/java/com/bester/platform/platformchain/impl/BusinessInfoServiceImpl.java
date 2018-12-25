package com.bester.platform.platformchain.impl;

import com.bester.platform.platformchain.dao.BusinessInfoDao;
import com.bester.platform.platformchain.dto.BusinessInfoDTO;
import com.bester.platform.platformchain.entity.BusinessInfoEntity;
import com.bester.platform.platformchain.service.BusinessInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author yanrui
 */
@Service
public class BusinessInfoServiceImpl implements BusinessInfoService{

    @Resource
    private BusinessInfoDao businessInfoDao;

    @Override
    public Integer insertBusinessInfo(BusinessInfoDTO businessInfoDTO) {
        BusinessInfoEntity businessInfoEntity = new BusinessInfoEntity();
        BeanUtils.copyProperties(businessInfoDTO,businessInfoEntity);
        businessInfoDao.insertBusinessInfo(businessInfoEntity);
        return businessInfoEntity.getId();
    }
}
