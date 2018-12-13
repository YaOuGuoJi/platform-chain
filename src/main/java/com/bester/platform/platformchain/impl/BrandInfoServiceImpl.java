package com.bester.platform.platformchain.impl;

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
    public List<BrandInfoDTO> selectBrandInfo(String BrandName) {
        List<BrandInfoEntity> brandInfoEntities = brandInfoDao.selectBrandInfo(BrandName);
        return BeansListUtils.copyListProperties(brandInfoEntities,BrandInfoDTO.class);
    }

    @Override
    public BrandInfoDTO selectBrandById(Integer BrandId) {
        BrandInfoEntity brandInfoEntity = brandInfoDao.selectBrandById(BrandId);
        if (brandInfoEntity == null){
            return null;
        }
        BrandInfoDTO brandInfoDTO = new BrandInfoDTO();
        BeanUtils.copyProperties(brandInfoEntity,brandInfoDTO);
        return brandInfoDTO;
    }
}
