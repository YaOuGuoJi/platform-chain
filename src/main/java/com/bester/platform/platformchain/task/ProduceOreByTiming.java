package com.bester.platform.platformchain.task;

import com.bester.platform.platformchain.constant.BlockChainParameters;
import com.bester.platform.platformchain.constant.OreRecordSource;
import com.bester.platform.platformchain.constant.OreRecordStatus;
import com.bester.platform.platformchain.constant.PowerStatus;
import com.bester.platform.platformchain.dao.OreRecordDao;
import com.bester.platform.platformchain.dao.PowerRecordDao;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @author zhangqiang
 */
@Component
public class ProduceOreByTiming {

    @Resource
    private PowerRecordDao powerRecordDao;

    @Resource
    private OreRecordDao oreRecordDao;

    @Scheduled(cron = BlockChainParameters.GROWING_INTERVAL)
    public void produceOre(Integer userId) {
        Integer allValidPower = powerRecordDao.findAllUserValidPower(PowerStatus.VALID);
        BigDecimal totalPower = new BigDecimal(allValidPower == null ? 1 : allValidPower);
        Integer validPower = powerRecordDao.findValidPower(userId, PowerStatus.VALID);
        BigDecimal userValidPower = new BigDecimal(validPower == null ? 0 : validPower);
        BigDecimal userPowerPercent = userValidPower.divide(totalPower, 5, BigDecimal.ROUND_HALF_UP);
        BigDecimal userOreByInterval = (BlockChainParameters.DAILY_ORE_LIMITED.multiply(userPowerPercent)).divide(new BigDecimal("8.00"), 5, BigDecimal.ROUND_HALF_UP);
        oreRecordDao.insertUserOreByInterval(userId, OreRecordSource.DAILY_RECEIVE, userOreByInterval, OreRecordStatus.PENDING);
    }

}
