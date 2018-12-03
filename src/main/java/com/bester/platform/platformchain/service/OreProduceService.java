package com.bester.platform.platformchain.service;

import java.math.BigDecimal;

/**
 * @author liuwen
 * @date 2018/11/15
 */
public interface OreProduceService {

    /**
     * 截止当前时间的总矿石产值
     *
     * @return
     */
    BigDecimal totalOreNumber();

    /**
     * 指定某年内每天的矿石产量
     *
     * @param year
     * @return
     */
    BigDecimal oreNumberByDay(int year);
}
