package com.bester.platform.platformchain.constant;

import java.math.BigDecimal;

/**
 * @author zhangqiang
 */
public class BlockChainParameters {

    /**
     * 每日发放矿石总数
     */
    public static final BigDecimal DAILY_ORE_LIMITED = new BigDecimal("10.00");

    /**
     * 临时算力过期天数
     */
    public static final int EXPIRATION_DAYS = 90;
}
