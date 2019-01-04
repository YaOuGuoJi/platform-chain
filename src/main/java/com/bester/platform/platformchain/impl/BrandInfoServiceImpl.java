package com.bester.platform.platformchain.impl;

import com.bester.platform.platformchain.constant.BrandActionType;
import com.bester.platform.platformchain.dao.BrandInfoDao;
import com.bester.platform.platformchain.dto.BrandInfoDTO;
import com.bester.platform.platformchain.entity.BrandInfoEntity;
import com.bester.platform.platformchain.service.BrandInfoService;
import com.bester.platform.platformchain.util.BeansListUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yanrui
 */
@Service
public class BrandInfoServiceImpl implements BrandInfoService {

    @Resource
    private BrandInfoDao brandInfoDao;

    @Override
    public List<BrandInfoDTO> selectBrandInfo(String brandName, Integer type, Integer floor) {
        List<BrandInfoEntity> brandInfoEntities = brandInfoDao.selectBrandInfo(brandName, type, floor);
        if (brandInfoEntities == null) {
            return null;
        }
        return BeansListUtils.copyListProperties(brandInfoEntities,BrandInfoDTO.class);
    }

    @Override
    public BrandInfoDTO selectBrandById(Integer brandId) {
        BrandInfoEntity brandInfoEntity = brandInfoDao.selectBrandById(brandId);
        if (brandInfoEntity == null){
            return null;
        }
        BrandInfoDTO brandInfoDTO = new BrandInfoDTO();
        BeanUtils.copyProperties(brandInfoEntity,brandInfoDTO);
        return brandInfoDTO;
    }

    @Override
    public int updatePraiseOrCollectNum(Integer brandId, Integer type, Integer number) {
        if (brandId == null || type == null || number == null) {
            return 0;
        }
        if (type == BrandActionType.PRAISE) {
            return brandInfoDao.updatePraise(brandId, number);
        } else if (type == BrandActionType.COLLECT) {
            return brandInfoDao.updateCollect(brandId, number);
        }
        return 0;
    }
}