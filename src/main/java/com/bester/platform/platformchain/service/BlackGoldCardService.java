package com.bester.platform.platformchain.service;

import com.bester.platform.platformchain.dto.BlackGoldCardDTO;

/**
 * @author liuwen
 * @date 2018/12/11
 */
public interface BlackGoldCardService {

    /**
     * 根据卡号查询黑金卡详细信息
     *
     * @param cardId
     * @return
     */
    BlackGoldCardDTO findBlackGoldCardByCardId(String cardId);

    /**
     * 黑金卡绑定到用户
     *
     * @param cardId
     * @param userId
     * @return
     */
    int bindCardToUser(String cardId, int userId);

    /**
     * 根据userId查询黑金卡信息
     *
     * @param userId
     * @return
     */
    BlackGoldCardDTO findBlackGoldCardInfoByUserId(int userId);

}
