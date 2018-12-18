package com.bester.platform.platformchain.impl;

import com.bester.platform.platformchain.dao.CouponDao;
import com.bester.platform.platformchain.dto.CouponDTO;
import com.bester.platform.platformchain.entity.CouponEntity;
import com.bester.platform.platformchain.service.CouponService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * @author zhangqiang
 * @date 2018-12-18
 */
@Service
public class CouponServiceImpl implements CouponService {

    @Resource
    private CouponDao couponDao;

    @Override
    public int addCoupon(CouponDTO couponDTO) {
        CouponEntity entity = new CouponEntity();
        entity.setCouponName(couponDTO.getCouponName());
        entity.setMargin(couponDTO.getMargin());
        entity.setCouponType(couponDTO.getCouponType());
        entity.setOfferCash(couponDTO.getOfferCash());
        entity.setOfferDiscount(couponDTO.getOfferDiscount());
        entity.setThreshold(couponDTO.getThreshold());
        entity.setVipLevel(couponDTO.getVipLevel());
        entity.setLimitNum(couponDTO.getLimitNum());
        entity.setValidityPeriod(couponDTO.getValidityPeriod());
        StringBuilder stringBuilder = new StringBuilder();
        if (!CollectionUtils.isEmpty(couponDTO.getAvailable())) {
            List<String> list = couponDTO.getAvailable();
            for (int i = 0; i < list.size(); i++) {
                stringBuilder.append(list.get(i));
                if (i != list.size() - 1) {
                    stringBuilder.append(",");
                }
            }
        }
        entity.setAvailable(stringBuilder.toString());
        entity.setDescription(couponDTO.getDescription());
        return couponDao.addCoupon(entity);
    }

    @Override
    public CouponDTO inquireCouponById(Integer couponId) {
        CouponEntity couponEntity = couponDao.inquireCouponById(couponId);
        CouponDTO dto = new CouponDTO();
        if (couponEntity != null) {
            dto.setCouponName(couponEntity.getCouponName());
            dto.setMargin(couponEntity.getMargin());
            dto.setCouponType(couponEntity.getCouponType());
            dto.setOfferCash(couponEntity.getOfferCash());
            dto.setOfferDiscount(couponEntity.getOfferDiscount());
            dto.setThreshold(couponEntity.getThreshold());
            dto.setVipLevel(couponEntity.getVipLevel());
            dto.setLimitNum(couponEntity.getLimitNum());
            dto.setValidityPeriod(couponEntity.getValidityPeriod());
            String[] availableList = couponEntity.getAvailable().split(",");
            List<String> list = Arrays.asList(availableList);
            dto.setAvailable(list);
            dto.setDescription(couponEntity.getDescription());
        }
        return dto;
    }
}
