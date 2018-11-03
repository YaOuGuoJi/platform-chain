package com.bester.platform.platformchain.task;


import com.bester.platform.platformchain.dao.PowerRecordDao;
import com.bester.platform.platformchain.dao.TotalPowerDao;
import com.bester.platform.platformchain.entity.PowerEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author yanrui
 */
@Component
public class TotalPowerTask {

    @Resource
    private PowerRecordDao powerRecordDao;
    @Resource
    private TotalPowerDao totalPowerDao;

    @Scheduled(cron = "0 0 0 * * ?")
   public void tatalPower(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now = df.format(new Date());
        PowerEntity powerEntity = new PowerEntity();
        int foreverPower = powerRecordDao.selectForeverPower();
        int temporaryPower = powerRecordDao.selectTemporaryPower();
        int totalPower = foreverPower + temporaryPower;
        totalPowerDao.insertTotalPower(totalPower);
   }

}
