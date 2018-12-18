package com.bester.platform.platformchain.impl;

import com.bester.platform.platformchain.dao.CouponDao;
import com.bester.platform.platformchain.dto.CouponDTO;
import com.bester.platform.platformchain.entity.CouponEntity;
import com.bester.platform.platformchain.service.CouponService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
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
        CouponEntity couponEntity = new CouponEntity();
        couponEntity.setCouponName(couponDTO.getCouponName());
        couponEntity.setMargin(couponDTO.getMargin());
        couponEntity.setCouponType(couponDTO.getCouponType());
        couponEntity.setCouponType(couponDTO.getCouponType());
        couponEntity.setOfferCash(couponDTO.getOfferCash());
        couponEntity.setOfferDiscount(couponDTO.getOfferDiscount());
        couponEntity.setThreshold(couponDTO.getThreshold());
        couponEntity.setVipLevel(couponDTO.getVipLevel());
        couponEntity.setLimitNum(couponDTO.getLimitNum());
        couponEntity.setValidityPeriod(couponDTO.getValidityPeriod());
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
        couponEntity.setAvailable(stringBuilder.toString());
        couponEntity.setDescription(couponDTO.getDescription());
        return couponDao.addCoupon(couponEntity);
    }
}
