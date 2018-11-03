package com.bester.platform.platformchain.task;

import com.bester.platform.platformchain.constant.BlockChainParameters;
import com.bester.platform.platformchain.constant.OreRecordSource;
import com.bester.platform.platformchain.constant.OreRecordStatus;
import com.bester.platform.platformchain.dao.OreRecordDao;
import com.bester.platform.platformchain.dao.PowerRecordDao;
import com.bester.platform.platformchain.dao.TotalPowerDao;
import com.bester.platform.platformchain.dao.UserInfoDao;
import com.bester.platform.platformchain.util.TemporaryPowerUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author zhangqiang
 */
@Component
public class ProduceOreByTiming {

    @Resource
    private TotalPowerDao totalPowerDao;

    @Resource
    private PowerRecordDao powerRecordDao;

    @Resource
    private UserInfoDao userInfoDao;

    @Resource
    private OreRecordDao oreRecordDao;

    @Scheduled(cron = BlockChainParameters.GROWING_INTERVAL)
    public void produceOre() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String today = simpleDateFormat.format(new Date());
        BigDecimal totalPower = new BigDecimal(totalPowerDao.getTotalPower(today));
        List<Integer> userIdList = userInfoDao.userIdList();
        userIdList.forEach(userId -> {
            Integer validTemporaryPower = powerRecordDao.getUserValidTemporaryPower(userId, TemporaryPowerUtil.expiredPowerTime());
            Integer userForeverPower = powerRecordDao.getUserForeverPower(userId);
            BigDecimal temporaryPower = new BigDecimal(validTemporaryPower == null ? 0 : validTemporaryPower);
            BigDecimal foreverPower = new BigDecimal(userForeverPower == null ? 0 : userForeverPower);
            BigDecimal userPower = temporaryPower.add(foreverPower);
            BigDecimal userPowerPercent = userPower.divide(totalPower, 5, BigDecimal.ROUND_HALF_UP);
            BigDecimal userOreByHour = (BlockChainParameters.DAILY_ORE_LIMITED.multiply(userPowerPercent)).divide(new BigDecimal("8.00"), 5, BigDecimal.ROUND_HALF_UP);
            Integer growingOreByEveryday = oreRecordDao.findGrowingOreByEveryday(userId, OreRecordStatus.PENDING);
            if (growingOreByEveryday <= BlockChainParameters.MAX_ORE_NUMBER) {
                oreRecordDao.insertUserOreByHour(userId, OreRecordSource.DAILY_RECEIVE, userOreByHour, OreRecordStatus.PENDING);
            }
        });
    }

}
