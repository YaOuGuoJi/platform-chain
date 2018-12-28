package com.bester.platform.platformchain.service;

import com.bester.platform.platformchain.dto.BusinessInfoDTO;

/**
 * @author yanrui
 */
public interface BusinessInfoService{

    /**
     * @param businessInfoDTO
     * @return
     */
    Integer insertBusinessInfo(BusinessInfoDTO businessInfoDTO);
}
