package com.bester.platform.platformchain.task;


import com.bester.platform.platformchain.constant.BlockChainParameters;
import com.bester.platform.platformchain.dao.PowerRecordDao;
import org.joda.time.DateTime;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author yanrui
 */
@Component
public class UpdateInvalidPowerTask {

    @Resource
    private PowerRecordDao powerRecordDao;

    @Scheduled(cron = BlockChainParameters.TOTAL_ORE_INTERVAL)
   public void updateInvalidPower(){
        powerRecordDao.updateTemporaryPower(new DateTime().minusDays(BlockChainParameters.EXPIRATION_DAYS).toDate());
   }

}
