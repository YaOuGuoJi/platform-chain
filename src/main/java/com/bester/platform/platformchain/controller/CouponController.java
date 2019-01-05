package com.bester.platform.platformchain.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bester.platform.platformchain.common.CommonResult;
import com.bester.platform.platformchain.enums.HttpStatus;
import com.bester.platform.platformchain.util.UserInfoUtil;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xianbester.api.constant.Coupon;
import com.xianbester.api.dto.CouponDTO;
import com.xianbester.api.dto.ShopInfoDTO;
import com.xianbester.api.dto.UserCouponDTO;
import com.xianbester.api.dto.UserInfoDTO;
import com.xianbester.api.service.CouponService;
import com.xianbester.api.service.ShopInfoService;
import com.xianbester.api.service.UserCouponService;
import com.xianbester.api.service.UserInfoService;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author zhangqiang
 * @date 2018-12-18
 */
@RestController
public class CouponController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CouponController.class);
    @Reference
    private CouponService couponService;
    @Reference
    private UserCouponService userCouponService;
    @Reference
    private UserInfoService userInfoService;
    @Reference
    private ShopInfoService shopInfoService;

    /**
     * 优惠券分页列表
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/coupon/list")
    public CommonResult getUserCouponInfo(int pageNum, int pageSize) {
        int userId = UserInfoUtil.getUserId();
        if (userId <= 0 || pageNum <= 0 || pageSize <= 0) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        PageInfo<CouponDTO> couponDTOPageInfo = couponService.queryAllCouponInfo(pageNum, pageSize);
        if (couponDTOPageInfo == null) {
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        List<CouponDTO> couponList = couponDTOPageInfo.getList();
        List<Integer> couponIdList = couponList.stream().map(CouponDTO::getId).collect(Collectors.toList());
        Map<Integer, Integer> couponUserCount = userCouponService.selectCouponCount(userId, couponIdList);
        couponList.forEach(couponDTO -> {
            int remainNum = couponDTO.getLimitNum() - couponUserCount.getOrDefault(couponDTO.getId(), 0);
            couponDTO.setLimitNum(remainNum);
        });
        return CommonResult.success(couponDTOPageInfo);
    }

    /**
     * 查找用户已使用、未使用、已过期优惠券列表
     *
     * @param status
     * @return
     */
    @GetMapping("/coupon/status")
    public CommonResult inquireCouponStatus(Integer status) {
        int userId = UserInfoUtil.getUserId();
        if (userId <= 0) {
            return CommonResult.fail(HttpStatus.UNAUTHORIZED);
        }
        if (status < 0) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        List<UserCouponDTO> userCouponDTOs = userCouponService.findUserCouponByStatus(userId, status);
        if (CollectionUtils.isEmpty(userCouponDTOs)) {
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        Set<Integer> couponIds = userCouponDTOs.stream().map(UserCouponDTO::getCouponId).collect(Collectors.toSet());
        Map<Integer, CouponDTO> couponId2CouponDTOMap = couponService.batchFindByCouponIds(couponIds);
        Map<Integer, ShopInfoDTO> shopId2ShopInfoMap = Maps.newHashMap();
        if (Coupon.USED.equals(status)) {
            Set<Integer> shopIds = userCouponDTOs.stream().map(UserCouponDTO::getShopId).collect(Collectors.toSet());
            shopId2ShopInfoMap.putAll(shopInfoService.batchFindByShopIds(shopIds));
        }
        List<UserCouponVO> userCouponList = Lists.newArrayListWithExpectedSize(couponIds.size());
        userCouponDTOs.forEach(userCoupon -> {
            UserCouponVO userCouponVO = new UserCouponVO();
            BeanUtils.copyProperties(couponId2CouponDTOMap.get(userCoupon.getCouponId()), userCouponVO);
            BeanUtils.copyProperties(userCoupon, userCouponVO);
            if (Coupon.USED.equals(status)) {
                ShopInfoDTO shopInfoDTO = shopId2ShopInfoMap.get(userCoupon.getShopId());
                userCouponVO.setShopId(Lists.newArrayList(String.valueOf(userCoupon.getShopId())));
                userCouponVO.setShopName(shopInfoDTO.getShopName());
            }
            userCouponList.add(userCouponVO);
        });
        return CommonResult.success(userCouponList);
    }

    /**
     * 用户领取优惠券
     *
     * @param couponId
     * @return
     */
    @PostMapping("/user/receive/coupon")
    public CommonResult receiveCoupon(Integer couponId) {
        if (couponId == null) {
            return CommonResult.fail(403, "参数错误");
        }
        CouponDTO couponDTO = couponService.inquireCouponById(couponId);
        if (couponDTO == null) {
            return CommonResult.fail(403, "参数错误");
        }
        int userId = UserInfoUtil.getUserId();
        UserInfoDTO userInfo = userInfoService.findUserInfoByUserId(userId);
        if (couponDTO.getVipLevel() > userInfo.getVip()) {
            return CommonResult.fail(403, "等级不够");
        }
        int couponCount = userCouponService.findCouponCountById(userId, couponDTO.getId());
        if (couponDTO.getLimitNum() <= couponCount) {
            return CommonResult.fail(403, "领取已达上限");
        }
        if (couponDTO.getMargin() <= 0) {
            return CommonResult.fail(404, "优惠券已抢光");
        }
        int affectCount = userCouponService.receiveCoupon(userId, couponId);
        if (affectCount == 0) {
            LOGGER.error("领取优惠券失败！userId: [{}], couponId: [{}]", userId, couponId);
            return CommonResult.fail(500, "领取失败,服务器异常");
        }
        return CommonResult.success();
    }

    @Data
    public class UserCouponVO {
        /**
         * 用户领取到的优惠券在userCoupon中的id
         */
        private Integer id;
        /**
         * 优惠券类型
         */
        private Integer couponType;
        /**
         * 商铺id(已用：在哪家店铺使用，未用：可以在哪家店铺使用)
         */
        private List<String> shopId;
        /**
         * 已用的优惠券展示使用的店铺名称
         */
        private String shopName;
        /**
         * 优惠券名字
         */
        private String couponName;
        /**
         * 折扣金额
         */
        private BigDecimal offerCash;
        /**
         * 折扣比例
         */
        private BigDecimal offerDiscount;
        /**
         * 使用说明
         */
        private String description;
        /**
         * 使用门槛（满XX元可用，为零则代表使用条件为无限制）
         */
        private BigDecimal threshold;
        /**
         * 失效时间
         */
        private Date failureTime;
        /**
         * 使用时间
         */
        private Date updateTime;
    }

}
