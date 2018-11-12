package com.bester.platform.platformchain.service;

import com.bester.platform.platformchain.dao.PowerRecordDao;
import com.bester.platform.platformchain.dto.OreRecordDTO;
import com.bester.platform.platformchain.entity.PowerEntity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OreRecordTest {
    @Resource
    private OreRecordService oreRecordService;

    @Resource
    private PowerRecordDao powerRecordDao;

    @Test
    public void showOreByUserIdTest() {
        List<OreRecordDTO> oreRecords = oreRecordService.showOreByUserId(100001);
        Assert.assertEquals(oreRecords.size(), 0);
    }

    @Test
    public void showOreByIdTest() {
        OreRecordDTO oreRecordDTO = oreRecordService.showOreById(1);
        Assert.assertNotNull(oreRecordDTO);
    }

    @Test
    public void receiveOreTest() {
        Integer integer = oreRecordService.receiveOre(1);
        Assert.assertEquals(integer, new Integer(0));
    }

    @Test
    public void insertPower(){
        PowerEntity powerEntity = new PowerEntity();
        String[] source = {"购物","游戏","小说","新闻","邀请好友"};
        for (int i = 5; i <= 30; i++){
            Random rd = new Random();
            powerEntity.setUserId(100001);
            powerEntity.setSource(source[(int)(Math.random()*source.length)]);
            powerEntity.setPower(20);
            powerEntity.setAddTime(new Date());
            powerEntity.setUpdateTime(new Date());
            powerEntity.setTemporary(0);
            powerEntity.setValid(rd.nextInt(2)+1);
            powerRecordDao.insertPower(powerEntity.getUserId(),powerEntity.getSource(),powerEntity.getPower(),powerEntity.getAddTime(),
                    powerEntity.getUpdateTime(),powerEntity.getTemporary(),powerEntity.getValid());
        }

    }
}
