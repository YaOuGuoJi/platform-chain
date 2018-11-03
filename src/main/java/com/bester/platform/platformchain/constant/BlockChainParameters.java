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

    /**
     * 矿值停止生长的时间间隔
     */
    public static final Integer STOP_GROWING_HOUR = 48;

    /**
     * 设定产出矿石的时间间隔
     */
    public static final String GROWING_INTERVAL = "0 0 0/3 * * ?";

    /**
     * 最大间隔内生成的矿石个数（根据产出矿石的间隔时间计算)
     */
    public static final Integer MAX_ORE_NUMBER = STOP_GROWING_HOUR/3;

}
