package com.bester.platform.platformchain.impl;

import com.bester.platform.platformchain.constant.BlockChainParameters;
import com.bester.platform.platformchain.service.OreProduceService;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    private static Date START_DATE;

    static {
        try {
            START_DATE = new SimpleDateFormat("yyyy-MM-dd").parse("2018-11-01");
        } catch (ParseException e) {
            LOGGER.error("转换时间异常！", e);
        }
    }

    @Override
    public BigDecimal totalOreNumber(Date date) {
        BigDecimal total = new BigDecimal(0);
        if (date.before(START_DATE)) {
            return total;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        int startYear = new DateTime(START_DATE).getYear();
        int thisYear = new DateTime(date).getYear();
        Date start = new Date();
        start.setTime(START_DATE.getTime());
        try {
            for (int year = startYear; year <= thisYear; year++) {
                Date endOfYear = sdf.parse(year + 1 + "-" + "01-01");
                Date end = date.before(endOfYear) ? date : endOfYear;
                BigDecimal yearTotal = this.buildYearTotal(start, end);
                total = total.add(yearTotal);
                start = end;
            }
        } catch (ParseException e) {
            LOGGER.error("转换时间异常！", e);
        }
        return total;
    }

    @Override
    public BigDecimal oreNumberByDay(int year) {
        int yearDiff = year - new DateTime(START_DATE).getYear();
        if (yearDiff < 0) {
            return new BigDecimal(0);
        }
        BigDecimal half = new BigDecimal(Math.pow(2, yearDiff));
        return BlockChainParameters.DAILY_ORE_LIMITED.divide(half, 5, RoundingMode.HALF_UP);
    }

    private BigDecimal buildYearTotal(Date start, Date end) {
        final int dayTime = 24 * 60 * 60 * 1000;
        final int year = new DateTime(start).getYear();
        int dayDiff = new Long((end.getTime() - start.getTime()) / dayTime).intValue();
        return this.oreNumberByDay(year).multiply(new BigDecimal(dayDiff));
    }
}
