package com.bester.platform.platformchain.dao;

import com.bester.platform.platformchain.entity.VoucherCardEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author liuwen
 * @date 2018/12/13
 */
public interface VoucherCardDao {

    /**
     * 添加代金券
     *
     * @param entityList
     * @return
     */
    int addCard(@Param("entityList") List<VoucherCardEntity> entityList);

    /**
     * 根据卡号查询
     *
     * @param cardId
     * @return
     */
    VoucherCardEntity findCardByCardId(@Param("cardId") String cardId);

    /**
     * 代金券绑定到用户
     *
     * @param cardId
     * @param userId
     * @return
     */
    int bindCard2User(@Param("cardId") String cardId, @Param("userId") int userId);

    /**
     * 查询用户已绑定的代金券
     *
     * @param userId
     * @return
     */
    List<VoucherCardEntity> findUserBindCards(@Param("userId") int userId);
}
