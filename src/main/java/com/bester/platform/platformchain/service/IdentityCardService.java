package com.bester.platform.platformchain.service;

import com.bester.platform.platformchain.dto.UserIdentityDTO;

import java.io.InputStream;

/**
 * @author liuwen
 */
public interface IdentityCardService {

    /**
     * OCR识别身份证信息
     *
     * @param inputStream
     * @return
     */
    UserIdentityDTO idCardOCR(InputStream inputStream);

    /**
     * OCR识别身份证信息
     *
     * @param imgData
     * @return
     */
    UserIdentityDTO idCardOCR(byte[] imgData);

}

