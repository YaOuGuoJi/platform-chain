package com.bester.platform.platformchain.util;

import com.bester.platform.platformchain.constant.BlockChainParameters;
import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author liuwen
 * @date 2018/11/3
 */
public class TemporaryPowerUtil {

    /**
     * 临时算力过期时间计算
     *
     * @return
     */
    public static Date expiredPowerTime() {
        String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        return new DateTime(today).minusDays(BlockChainParameters.EXPIRATION_DAYS).toDate();
    }

    /**
     * 待领取矿石过期时间计算
     *
     * @return
     */
    public static Date overdueOreTime() {
        return new DateTime().minusDays(BlockChainParameters.DAILY_ORE_OVERDUE).toDate();
    }
}
