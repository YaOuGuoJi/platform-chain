package com.bester.platform.platformchain.task;


import com.bester.platform.platformchain.constant.BlockChainParameters;
import com.bester.platform.platformchain.dao.PowerRecordDao;
import com.bester.platform.platformchain.dao.TotalPowerDao;
import org.joda.time.DateTime;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author yanrui
 */
@Component
public class TotalPowerTask {

    @Resource
    private PowerRecordDao powerRecordDao;
    @Resource
    private TotalPowerDao totalPowerDao;

    @Scheduled(cron = BlockChainParameters.TOTAL_ORE_INTERVAL)
   public void tatalPower(){
        int foreverPower = powerRecordDao.selectForeverPower();
        int temporaryPower = powerRecordDao.selectTemporaryPower(new DateTime().minusDays(BlockChainParameters.EXPIRATION_DAYS).toDate());
        int totalPower = foreverPower + temporaryPower;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String day = df.format(new Date());
        totalPowerDao.insertTotalPower(day,totalPower);
   }

}
