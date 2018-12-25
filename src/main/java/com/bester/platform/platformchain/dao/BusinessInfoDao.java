package com.bester.platform.platformchain.dao;

import com.bester.platform.platformchain.entity.BusinessInfoEntity;
import org.apache.ibatis.annotations.Param;

/**
 * @author yanrui
 */
public interface BusinessInfoDao {

    int insertBusinessInfo(@Param("businessInfo")BusinessInfoEntity businessInfoEntity);
}
