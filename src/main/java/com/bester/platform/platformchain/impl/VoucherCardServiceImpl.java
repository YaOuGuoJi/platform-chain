package com.bester.platform.platformchain.impl;

import com.bester.platform.platformchain.dao.VoucherCardDao;
import com.bester.platform.platformchain.dto.VoucherCardDTO;
import com.bester.platform.platformchain.entity.VoucherCardEntity;
import com.bester.platform.platformchain.service.VoucherCardService;
import com.bester.platform.platformchain.util.BeansListUtils;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author liuwen
 * @date 2018/12/13
 */
@Service
public class VoucherCardServiceImpl implements VoucherCardService {

    @Resource
    private VoucherCardDao voucherCardDao;

    @Override
    public VoucherCardDTO findVoucherCardById(String cardId) {
        VoucherCardEntity entity = voucherCardDao.findCardByCardId(cardId);
        if (entity == null) {
            return null;
        }
        VoucherCardDTO voucherCardDTO = new VoucherCardDTO();
        BeanUtils.copyProperties(entity, voucherCardDTO);
        return voucherCardDTO;
    }

    @Override
    public int bindVoucherCard2User(String cardId, int userId) {
        return voucherCardDao.bindCard2User(cardId, userId);
    }

    @Override
    public List<VoucherCardDTO> findUserBindVouchers(int userId) {
        List<VoucherCardEntity> userBindCards = voucherCardDao.findUserBindCards(userId);
        if (CollectionUtils.isEmpty(userBindCards)) {
            return Lists.newArrayList();
        }
        return BeansListUtils.copyListProperties(userBindCards, VoucherCardDTO.class);
    }
}
