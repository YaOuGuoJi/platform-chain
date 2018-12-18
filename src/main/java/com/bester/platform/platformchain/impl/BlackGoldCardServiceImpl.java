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
        if (entity == null) {
            return null;
        }
        BlackGoldCardDTO blackGoldCardDTO = new BlackGoldCardDTO();
        BeanUtils.copyProperties(entity, blackGoldCardDTO);
        return blackGoldCardDTO;
    }

    @Override
    public int bindCardToUser(String cardId, int userId) {
        return blackGoldCardDao.bindCard2User(cardId, userId);
    }
}
