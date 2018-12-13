package com.bester.platform.platformchain.service;

import com.bester.platform.platformchain.dto.VoucherCardDTO;

import java.util.List;

/**
 * @author liuwen
 * @date 2018/12/13
 */
public interface VoucherCardService {

    /**
     * 根据代金券卡号查询详情
     *
     * @param cardId
     * @return
     */
    VoucherCardDTO findVoucherCardById(String cardId);

    /**
     * 绑定代金券到用户
     *
     * @param cardId
     * @param userId
     * @return
     */
    int bindVoucherCard2User(String cardId, int userId);

    /**
     * 查询用户绑定的代金券
     *
     * @param userId
     * @return
     */
    List<VoucherCardDTO> findUserBindVouchers(int userId);
}
