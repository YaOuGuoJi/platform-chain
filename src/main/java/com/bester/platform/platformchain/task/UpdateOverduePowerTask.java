package com.bester.platform.platformchain.task;

import com.bester.platform.platformchain.constant.BlockChainParameters;
import com.bester.platform.platformchain.dao.OreRecordDao;
import org.joda.time.DateTime;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author yanrui
 */
@Component
public class UpdateOverduePowerTask {

    @Resource
    private OreRecordDao oreRecordDao;

    @Scheduled(cron = "0 0 * * * ?")
    public void updateOverduePower(){
        Date time  = (new DateTime().minusDays(BlockChainParameters.DAILY_ORE_OVERDUE)).toDate();
        oreRecordDao.updateOverduePower(time);
    }
}
