package com.bester.platform.platformchain.impl;

import com.bester.platform.platformchain.dao.BlackGoldCardDao;
import com.bester.platform.platformchain.dto.BlackGoldCardDTO;
import com.bester.platform.platformchain.entity.BlackGoldCardEntity;
import com.bester.platform.platformchain.service.BlackGoldCardService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author liuwen
 * @date 2018/12/11
 */
@Service
public class BlackGoldCardServiceImpl implements BlackGoldCardService {

    @Resource
    private BlackGoldCardDao blackGoldCardDao;

    @Override
    public BlackGoldCardDTO findBlackGoldCardByCardId(String cardId) {
        BlackGoldCardEntity entity = blackGoldCardDao.findCardByCardId(cardId);
        return getBlackGoldCardDTO(entity);
    }

    @Override
    public int bindCardToUser(String cardId, int userId) {
        return blackGoldCardDao.bindCard2User(cardId, userId);
    }

    @Override
    public BlackGoldCardDTO findBlackGoldCardInfoByUserId(int userId) {
        BlackGoldCardEntity blackGoldCardEntity = blackGoldCardDao.findCardInfoByUserId(userId);
        return getBlackGoldCardDTO(blackGoldCardEntity);
    }

    private BlackGoldCardDTO getBlackGoldCardDTO(BlackGoldCardEntity blackGoldCardEntity) {
        if (blackGoldCardEntity == null) {
            return null;
        }
        BlackGoldCardDTO blackGoldCardDTO = new BlackGoldCardDTO();
        BeanUtils.copyProperties(blackGoldCardEntity, blackGoldCardDTO);
        return blackGoldCardDTO;
    }
}
