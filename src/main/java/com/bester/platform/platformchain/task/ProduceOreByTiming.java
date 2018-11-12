package com.bester.platform.platformchain.task;

import com.bester.platform.platformchain.constant.BlockChainParameters;
import com.bester.platform.platformchain.constant.OreRecordSource;
import com.bester.platform.platformchain.constant.OreRecordStatus;
import com.bester.platform.platformchain.constant.PowerStatus;
import com.bester.platform.platformchain.dao.OreRecordDao;
import com.bester.platform.platformchain.dao.PowerRecordDao;
import com.bester.platform.platformchain.dao.UserLoginDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(ProduceOreByTiming.class);
    private static BigDecimal allValidPower;

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
        Integer all = powerRecordDao.findAllUserValidPower(PowerStatus.VALID);
        if (all <= 0) {
            LOGGER.error("错误！当前总有效算力为" + all);
            throw new RuntimeException("错误！当前总有效算力为" + all);
        }
        allValidPower = new BigDecimal(all);
        userIdList.forEach(userId -> {
            Integer countOreByInterval = oreRecordDao.findGrowingOreByInterval(userId, OreRecordStatus.PENDING);
            if (countOreByInterval < 0) {
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
                }
            } else if (countOreByInterval < BlockChainParameters.MAX_ORE_NUMBER) {
                produceOre(userId);
            }
        });
    }

    /**
     * 计算用户当次被分配的矿石
     *
     * 计算公式:  (当天发放矿石总数C/每天发放次数T)*(用户有效算力V/总算力A) = (C*V)/(T*A)
     *
     * @param userId
     */
    private void produceOre(Integer userId) {
        int validPower = powerRecordDao.findValidPower(userId, PowerStatus.VALID);
        if (validPower <= 0) {
            return;
        }
        BigDecimal userValidPower = new BigDecimal(validPower);
        BigDecimal userOreByInterval = BlockChainParameters.DAILY_ORE_LIMITED.multiply(userValidPower)
                .divide(BlockChainParameters.TIMES_BY_DAY.multiply(allValidPower), 5, BigDecimal.ROUND_HALF_UP);
        oreRecordDao.insertUserOreByInterval(userId, OreRecordSource.DAILY_RECEIVE, userOreByInterval, OreRecordStatus.PENDING);
    }

}
