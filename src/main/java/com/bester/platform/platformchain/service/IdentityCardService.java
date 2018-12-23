package com.bester.platform.platformchain.service;

import com.bester.platform.platformchain.dto.IDCardDTO;

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
    IDCardDTO idCardOCR(InputStream inputStream);

    /**
     * OCR识别身份证信息
     *
     * @param imgData
     * @return
     */
    IDCardDTO idCardOCR(byte[] imgData);

}

