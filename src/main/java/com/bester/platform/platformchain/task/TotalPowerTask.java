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

    @Scheduled(cron = BlockChainParameters.TOTAL_ORE_INTERVAL)
   public void tatalPower(){
        powerRecordDao.updateTemporaryPower(new DateTime().minusDays(BlockChainParameters.EXPIRATION_DAYS).toDate());
   }

}
