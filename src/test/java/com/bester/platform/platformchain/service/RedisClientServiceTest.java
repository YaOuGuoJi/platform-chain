package com.bester.platform.platformchain.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author liuwen
 * @date 2018/11/30
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisClientServiceTest {

    @Resource
    private RedisClientService redisClientService;

    @Test
    public void test() throws InterruptedException {
        String name = "testName";
        redisClientService.set(name, "hello, world", 1000L);
        String result = (String) redisClientService.get(name);
        Assert.assertEquals("hello, world", result);
        Thread.sleep(1000L);
        result = (String) redisClientService.get("testName");
        Assert.assertNull(result);
    }
}
