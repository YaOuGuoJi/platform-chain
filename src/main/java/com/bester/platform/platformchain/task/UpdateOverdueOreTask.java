package com.bester.platform.platformchain.task;

import com.bester.platform.platformchain.constant.BlockChainParameters;
import com.bester.platform.platformchain.dao.OreRecordDao;
import com.bester.platform.platformchain.entity.OreRecordEntity;
import org.joda.time.DateTime;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author yanrui
 */
@Component
public class UpdateOverdueOreTask {

    @Resource
    private OreRecordDao oreRecordDao;

    @Scheduled(cron = BlockChainParameters.OVERDUE_INTERVAL)
    public void updateOverdueOre(){
        List<OreRecordEntity> oreRecordEntityList = oreRecordDao.selectMaxUpdateTime();
        oreRecordEntityList.forEach(oreRecordEntity -> {
            Date updateTime = oreRecordEntity.getUpdateTime();
            Integer status = oreRecordEntity.getStatus();
            Integer userId = oreRecordEntity.getUserId();
            Date limitTime = new DateTime().minusDays(BlockChainParameters.DAILY_ORE_OVERDUE).toDate();
            if (status.equals(1) || updateTime.before(limitTime)){
                oreRecordDao.updateOverduePower(userId);
            }
        });

    }
}
