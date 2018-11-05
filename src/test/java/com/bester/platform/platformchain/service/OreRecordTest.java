package com.bester.platform.platformchain.service;

import com.bester.platform.platformchain.dto.OreRecordDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OreRecordTest {
    @Resource
    private OreRecordService oreRecordService;
    @Test
    public void showOreByUserIdTest() {
        List<OreRecordDTO> oreRecords = oreRecordService.showOreByUserId(1);
        Assert.assertNotNull(oreRecords);
    }
    @Test
    public void showOreByIdTest(){
        OreRecordDTO oreRecordDTO = oreRecordService.showOreById(1);
        Assert.assertNull(oreRecordDTO);
    }
    @Test
    public void receiveOreTest(){
        Integer integer = oreRecordService.receiveOre(1);
        Assert.assertEquals(integer,new Integer(0));
    }
}
