package com.bester.platform.platformchain.impl;

import com.bester.platform.platformchain.dao.CouponDao;
import com.bester.platform.platformchain.dao.UserCouponDao;
import com.bester.platform.platformchain.dto.CouponDTO;
import com.bester.platform.platformchain.entity.CouponEntity;
import com.bester.platform.platformchain.service.CouponService;
import com.bester.platform.platformchain.util.BeansListUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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


}
