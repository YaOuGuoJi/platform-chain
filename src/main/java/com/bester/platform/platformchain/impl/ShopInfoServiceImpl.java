package com.bester.platform.platformchain.impl;

import com.bester.platform.platformchain.dao.ShopInfoDao;
import com.bester.platform.platformchain.dto.ShopInfoDTO;
import com.bester.platform.platformchain.entity.ShopInfoEntity;
import com.bester.platform.platformchain.service.ShopInfoService;
import com.bester.platform.platformchain.util.BeansListUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author liuwen
 * @date 2018/12/29
 */
@Service
public class ShopInfoServiceImpl implements ShopInfoService {

    @Resource
    private ShopInfoDao shopInfoDao;

    @Override
    public Map<Integer, ShopInfoDTO> batchFindByShopIds(Collection<Integer> shopIds) {
        if (CollectionUtils.isEmpty(shopIds)) {
            return Collections.emptyMap();
        }
        List<ShopInfoEntity> entityList = shopInfoDao.batchSelect(shopIds);
        List<ShopInfoDTO> shopInfoDTOList = BeansListUtils.copyListProperties(entityList, ShopInfoDTO.class);
        return shopInfoDTOList.stream().collect(Collectors.toMap(ShopInfoDTO::getShopId, shopInfoDTO -> shopInfoDTO));
    }
}
