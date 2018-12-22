package com.bester.platform.platformchain.impl;

import com.bester.platform.platformchain.dao.CouponDao;
import com.bester.platform.platformchain.dto.CouponDTO;
import com.bester.platform.platformchain.entity.CouponEntity;
import com.bester.platform.platformchain.service.CouponService;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import com.bester.platform.platformchain.util.BeansListUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import org.springframework.util.Assert;

import java.util.ArrayList;

/**
 * @author zhangqiang
 * @date 2018-12-18
 */
@Service
public class CouponServiceImpl implements CouponService {
    @Resource
    private CouponDao couponDao;
    @Resource
    private UserCouponDao userCouponDao;

    @Resource
    public PageInfo<CouponDTO> queryAllCouponInfo(int id, int pageNum, int pageSize) {
        Assert.isTrue(id > 0 && pageNum > 0 && pageSize > 0, "参数错误");
        List<Integer> ids = new ArrayList<>();
        List<CouponEntity> couponEntities = couponDao.queryAllCouponInfo();
        if (couponEntities == null) {
            return null;
        }
        for (CouponEntity couponEntity : couponEntities) {
            int couponNum = userCouponDao.queryUserCouponCount(id, couponEntity.getId());
            int couponMax = couponEntity.getLimitNum();
            if (couponNum < couponMax) {
                ids.add(couponEntity.getId());
            }
        }
        couponEntities = null;
        for (int data:ids) {
            couponEntities.add(couponDao.inquireCouponById(data));
        }
        PageHelper.startPage(pageNum, pageSize, true);
        return BeansListUtils.copyListPageInfo(couponEntities, CouponDTO.class);
    }

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
            dto.setId(couponEntity.getId());
            dto.setCouponName(couponEntity.getCouponName());
            String[] shopIdList = couponEntity.getShopId().split(",");
            List<String> shopId = Lists.newArrayList(shopIdList);
            dto.setShopId(shopId);
            dto.setMargin(couponEntity.getMargin());
            dto.setCouponType(couponEntity.getCouponType());
            dto.setOfferCash(couponEntity.getOfferCash());
            dto.setOfferDiscount(couponEntity.getOfferDiscount());
            dto.setThreshold(couponEntity.getThreshold());
            dto.setVipLevel(couponEntity.getVipLevel());
            dto.setLimitNum(couponEntity.getLimitNum());
            dto.setValidityPeriod(couponEntity.getValidityPeriod());
            String[] availableList = couponEntity.getAvailable().split(",");
            List<String> list = Lists.newArrayList(availableList);
            dto.setAvailable(list);
            dto.setDescription(couponEntity.getDescription());
            dto.setAddTime(couponEntity.getAddTime());
            dto.setUpdateTime(couponEntity.getUpdateTime());
        }
        return dto;
    }

    @Override
    public int updateCouponInfo(CouponDTO coupon) {
        if (coupon == null) {
            return 0;
        }
        if (coupon.getId() == null) {
            return 0;
        }
        String shopId = CollectionUtils.isEmpty(coupon.getShopId()) ? null : String.join(",", coupon.getShopId());
        CouponEntity couponEntity = new CouponEntity();
        BeanUtils.copyProperties(coupon, couponEntity);
        couponEntity.setShopId(shopId);
        return couponDao.updateCouponInfo(couponEntity);
    }
}
