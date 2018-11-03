package com.bester.platform.platformchain.task;

import com.bester.platform.platformchain.constant.BlockChainParameters;
import com.bester.platform.platformchain.dao.OreRecordDao;
import com.bester.platform.platformchain.entity.OreRecordEntity;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author yanrui
 */
@Component
public class UpdateOverduePowerTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateOverduePowerTask.class);

    @Resource
    private OreRecordDao oreRecordDao;

    @Scheduled(cron = "0 0 * * * ?")
    public void updateOverduePower(){
        List<OreRecordEntity> oreRecordEntityList = oreRecordDao.selectOverduePower();
        if (oreRecordEntityList.isEmpty()){
            LOGGER.info("未查到待领取矿石");
        }
        oreRecordEntityList.forEach(oreRecord ->{
            Date addTime = oreRecord.getAddTime();
            Date overdueDay  = (new DateTime().minusDays(BlockChainParameters.DAILY_ORE_OVERDUE)).toDate();
            if (overdueDay.before(addTime)){
                oreRecordDao.updateOverduePower();
            }
        });
    }
}
