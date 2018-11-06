package com.bester.platform.platformchain.task;

import com.bester.platform.platformchain.constant.BlockChainParameters;
import com.bester.platform.platformchain.constant.OreRecordSource;
import com.bester.platform.platformchain.constant.OreRecordStatus;
import com.bester.platform.platformchain.constant.PowerStatus;
import com.bester.platform.platformchain.dao.OreRecordDao;
import com.bester.platform.platformchain.dao.PowerRecordDao;
import com.bester.platform.platformchain.dao.UserLoginDao;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author zhangqiang
 */
@Component
public class ProduceOreByTiming {

    @Resource
    private PowerRecordDao powerRecordDao;

    @Resource
    private OreRecordDao oreRecordDao;

    @Resource
    private UserLoginDao userLoginDao;

    @Scheduled(cron = BlockChainParameters.GROWING_INTERVAL)
    public void judgingProductionConditions() {
        List<Integer> userIdList = powerRecordDao.userIdList();
        if (CollectionUtils.isEmpty(userIdList)) {
            return;
        }
        userIdList.forEach(userId -> {
            Integer countOreByInterval = oreRecordDao.findGrowingOreByInterval(userId, OreRecordStatus.PENDING);
            if (countOreByInterval == null) {
                return;
            }
            if (countOreByInterval == 0) {
                Date userLastLoginTime = userLoginDao.findUserLastLoginTime(userId);
                if (userLastLoginTime == null) {
                    return;
                }
                Date now = new Date();
                Long timeDiff = now.getTime() - userLastLoginTime.getTime();
                if (timeDiff > 0 && timeDiff < BlockChainParameters.INTERVAL) {
                    produceOre(userId);
                } else {
                    return;
                }
            } else if (countOreByInterval > 0 && countOreByInterval < BlockChainParameters.MAX_ORE_NUMBER) {
                produceOre(userId);
            } else {
                return;
            }
        });

    }

    private void produceOre(Integer userId) {
        Integer allValidPower = powerRecordDao.findAllUserValidPower(PowerStatus.VALID);
        BigDecimal totalPower = new BigDecimal(allValidPower == null ? 1 : allValidPower);
        Integer validPower = powerRecordDao.findValidPower(userId, PowerStatus.VALID);
        BigDecimal userValidPower = new BigDecimal(validPower == null ? 0 : validPower);
        BigDecimal userPowerPercent = userValidPower.divide(totalPower, 5, BigDecimal.ROUND_HALF_UP);
        BigDecimal userOreByInterval = (BlockChainParameters.DAILY_ORE_LIMITED.multiply(userPowerPercent)).divide(new BigDecimal("8.00"), 5, BigDecimal.ROUND_HALF_UP);
        oreRecordDao.insertUserOreByInterval(userId, OreRecordSource.DAILY_RECEIVE, userOreByInterval, OreRecordStatus.PENDING);
    }

}
