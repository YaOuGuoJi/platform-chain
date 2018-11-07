package com.bester.platform.platformchain.task;

import com.bester.platform.platformchain.constant.BlockChainParameters;
import com.bester.platform.platformchain.dao.OreRecordDao;
import com.bester.platform.platformchain.util.TemporaryPowerUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yanrui
 */
@Component
public class UpdateOverdueOreTask {

    /**
     * 更新list最大长度
     */
    private static final int LIST_LIMIT = 100;

    @Resource
    private OreRecordDao oreRecordDao;

    @Scheduled(cron = BlockChainParameters.OVERDUE_INTERVAL)
    public void updateOverdueOre() {
        List<Integer> userIdLists = oreRecordDao.selectMaxUpdateTime(TemporaryPowerUtil.overdueOreTime());
        if (CollectionUtils.isEmpty(userIdLists)) {
            return;
        }
        if (userIdLists.size() > LIST_LIMIT) {
            int part = userIdLists.size() / LIST_LIMIT;
            for(int i = 0; i < part; i++) {
                List<Integer> subList = userIdLists.subList(0, LIST_LIMIT);
                oreRecordDao.updateOverduePower(subList);
                userIdLists.subList(0, LIST_LIMIT).clear();
            }
        }
        if (CollectionUtils.isEmpty(userIdLists)) {
            oreRecordDao.updateOverduePower(userIdLists);
        }
    }
}
