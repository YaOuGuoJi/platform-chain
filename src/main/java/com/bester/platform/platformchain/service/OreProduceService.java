package com.bester.platform.platformchain.service;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author liuwen
 * @date 2018/11/15
 */
public interface OreProduceService {

    /**
     * 截止指定日期的总矿石产值
     *
     * @date 时间格式: yyyy-MM-dd
     * @return
     */
    BigDecimal totalOreNumber(Date date);

    /**
     * 指定年份的单日矿石产量
     *
     * @param year
     * @return
     */
    BigDecimal oreNumberByDay(int year);
}
