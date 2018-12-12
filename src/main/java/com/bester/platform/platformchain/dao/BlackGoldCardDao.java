package com.bester.platform.platformchain.dao;

import com.bester.platform.platformchain.entity.BlackGoldCardEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author liuwen
 * @date 2018/12/11
 */
public interface BlackGoldCardDao {

    /**
     * 添加一批黑金卡
     *
     * @param entities
     * @return
     */
    int addBlackGoldCards(@Param("entities") List<BlackGoldCardEntity> entities);

    /**
     * 根据卡号查询
     *
     * @param cardId
     * @return
     */
    BlackGoldCardEntity findCardByCardId(@Param("cardId") String cardId);

    /**
     * 用户绑定
     *
     * @param cardId
     * @param userId
     * @return
     */
    int bindCard2User(@Param("cardId") String cardId, @Param("userId") int userId);

}
