package com.bester.platform.platformchain.taskTest;

import com.bester.platform.platformchain.dao.PowerRecordDao;
import com.bester.platform.platformchain.entity.PowerEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TotalPowerTest {

    @Resource
    private PowerRecordDao powerRecordDao;

    @Test
    public void totalPowerTask(){
        PowerEntity powerEntity = new PowerEntity();
        powerEntity.setId(1000000001);
        powerEntity.setPower(20);
        powerEntity.setSource("2");
        powerEntity.setUserId(100000000);
        powerEntity.setTemporary(1);
        powerEntity.setAddTime(new Date());
        powerEntity.setUpdateTime(new Date());


    }
}
