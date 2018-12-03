package com.bester.platform.platformchain.impl;

import com.bester.platform.platformchain.constant.BlockChainParameters;
import com.bester.platform.platformchain.service.OreProduceService;
import com.bester.platform.platformchain.service.RedisClientService;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.bester.platform.platformchain.constant.RedisKeys.DAY_ORE_NUMBER_KEY;
import static com.bester.platform.platformchain.constant.RedisKeys.TOTAL_ORE_NUMBER_KEY;

/**
 * @author liuwen
 * @date 2018/11/15
 */
@Service
public class OreProductServiceImpl implements OreProduceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OreProductServiceImpl.class);
    /**
     * 起始发币日期: 2018年11月1日
     */
    private static final String START_DATE = "2018-11-01";

    @Resource
    private RedisClientService redisClientService;

    @Override
    public BigDecimal totalOreNumber() {
        BigDecimal total = (BigDecimal) redisClientService.get(TOTAL_ORE_NUMBER_KEY);
        if (total != null) {
            return total;
        }
        total = new BigDecimal("0");
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date start = sdf.parse(START_DATE);
            Date now = new Date();
            int startYear = new DateTime(START_DATE).getYear();
            int thisYear = new DateTime().getYear();
            for (int year = startYear; year <= thisYear; year++) {
                Date endOfYear = sdf.parse(year + 1 + "-" + "01-01");
                Date end = now.before(endOfYear) ? now : endOfYear;
                BigDecimal yearTotal = this.buildYearTotal(start, end);
                total = total.add(yearTotal);
                start = end;
            }
        } catch (ParseException e) {
            LOGGER.error("转换时间异常！", e);
            return null;
        }
        redisClientService.set(TOTAL_ORE_NUMBER_KEY, total, getNextDay());
        return total;
    }

    @Override
    public BigDecimal oreNumberByDay(int year) {
        int yearDiff = year - new DateTime(START_DATE).getYear();
        Assert.isTrue(yearDiff >= 0, "起始年份不得小于2018年!");
        BigDecimal result = (BigDecimal) redisClientService.get(DAY_ORE_NUMBER_KEY + year);
        if (result != null) {
            return result;
        }
        BigDecimal half = new BigDecimal(Math.pow(2, yearDiff));
        result = BlockChainParameters.DAILY_ORE_LIMITED.divide(half, 5, RoundingMode.HALF_UP);
        redisClientService.set(DAY_ORE_NUMBER_KEY + year, result, getNextDay());
        return result;
    }

    private BigDecimal buildYearTotal(Date start, Date end) {
        final int dayTime = 24 * 60 * 60 * 1000;
        final int year = new DateTime(start).getYear();
        int dayDiff = new Long((end.getTime() - start.getTime()) / dayTime).intValue();
        return this.oreNumberByDay(year).multiply(new BigDecimal(dayDiff));
    }

    private Date getNextDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new DateTime(calendar.getTime()).plusDays(1).toDate();
    }
}
