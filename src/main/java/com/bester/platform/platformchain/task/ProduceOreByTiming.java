package com.bester.platform.platformchain.task;

import com.bester.platform.platformchain.constant.BlockChainParameters;
import com.bester.platform.platformchain.dao.PowerRecordDao;
import com.bester.platform.platformchain.dao.TotalPowerDao;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zhangqiang
 */
@Component
public class ProduceOreByTiming {

    private static BigDecimal totalPower = new BigDecimal("0.00");

    @Resource
    private TotalPowerDao totalPowerDao;

    @Resource
    private PowerRecordDao powerRecordDao;

    @Scheduled(cron = "0 30 * * * ?")
    public void produceOre(Integer userId) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String today = simpleDateFormat.format(new Date());
        BigDecimal temporaryPower = new BigDecimal(powerRecordDao.getUserValidTemporaryPower(userId, BlockChainParameters.EXPIRATION_DAYS));
        BigDecimal foreverPower = new BigDecimal(powerRecordDao.getUserForeverPower(userId));
        BigDecimal userPower = temporaryPower.add(foreverPower);
        BigDecimal userPowerPercent = userPower.divide(ProduceOreByTiming.totalPower, 2, BigDecimal.ROUND_HALF_UP);
        BigDecimal userPowerByHour = (ProduceOreByTiming.totalPower.multiply(userPowerPercent)).divide(new BigDecimal("24.00"),5, BigDecimal.ROUND_HALF_UP);
    }

}
