package com.bester.platform.platformchain.service;

import com.bester.platform.platformchain.constant.RedisKeys;
import com.bester.platform.platformchain.dto.ShoppingDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

    @Test
    public void testInsert() {
        List<ShoppingDTO> shoppingDTOList = new ArrayList<>();
        ShoppingDTO shoppingDTO = new ShoppingDTO();
        shoppingDTO.setCommodityId(574867290284L);
        shoppingDTO.setCommodityName("有线电脑鼠标家用办公常用USB电竞游戏鼠标台式牧马人 9.9包邮");
        shoppingDTO.setMainImageUrl("http://img.alicdn.com/bao/uploaded/i4/3670180162/O1CN011D4EbqhPVtQE5bU_!!3670180162.jpg");
        shoppingDTO.setCommodityDetailUrl("http://item.taobao.com/item.htm?id=574867290284");
        shoppingDTO.setPrice(new BigDecimal("3.56"));
        shoppingDTO.setSalesVolume(0);
        shoppingDTO.setShortUrl("https://s.click.taobao.com/cdYNLJw");
        shoppingDTOList.add(shoppingDTO);
        ShoppingDTO gameMouse = new ShoppingDTO();
        gameMouse.setCommodityId(577949864722L);
        gameMouse.setCommodityName("索魂F8游戏有线鼠标电竞专业家用办公笔记本台式电脑USB鼠标");
        gameMouse.setMainImageUrl("http://img.alicdn.com/bao/uploaded/i4/3694365652/O1CN011rcf1b3cXwLqjY9_!!3694365652.jpg");
        gameMouse.setCommodityDetailUrl("http://item.taobao.com/item.htm?id=577949864722");
        gameMouse.setPrice(new BigDecimal("5.80"));
        gameMouse.setSalesVolume(0);
        gameMouse.setShortUrl("https://s.click.taobao.com/2EfNLJw");
        shoppingDTOList.add(gameMouse);
        ShoppingDTO m100Mouse = new ShoppingDTO();
        m100Mouse.setCommodityId(577950148590L);
        m100Mouse.setCommodityName("/M100R 鼠标有线 电脑台式机笔记本专用外设光电鼠标");
        m100Mouse.setMainImageUrl("http://img.alicdn.com/bao/uploaded/i4/3694365652/O1CN011rcf1ZJm0WjT96k_!!3694365652.jpg");
        m100Mouse.setCommodityDetailUrl("http://item.taobao.com/item.htm?id=577950148590");
        m100Mouse.setPrice(new BigDecimal("18.68"));
        m100Mouse.setSalesVolume(0);
        m100Mouse.setShortUrl("https://s.click.taobao.com/SycNLJw");
        shoppingDTOList.add(m100Mouse);
        ShoppingDTO fm100Mouse = new ShoppingDTO();
        fm100Mouse.setCommodityId(578122737337L);
        fm100Mouse.setCommodityName("包邮HP惠普FM100有线鼠标笔记本台式电脑USB接口通用办公游戏鼠标");
        fm100Mouse.setMainImageUrl("http://img.alicdn.com/bao/uploaded/i3/3694365652/O1CN011rcf1Zggm94koxA_!!3694365652.jpg");
        fm100Mouse.setCommodityDetailUrl("http://item.taobao.com/item.htm?id=578122737337");
        fm100Mouse.setPrice(new BigDecimal("5.31"));
        fm100Mouse.setSalesVolume(1);
        fm100Mouse.setShortUrl("https://s.click.taobao.com/PzgNLJw");
        shoppingDTOList.add(fm100Mouse);
        ShoppingDTO silenceMouse = new ShoppingDTO();
        silenceMouse.setCommodityId(578243398082L);
        silenceMouse.setCommodityName("无声静音卡通伸缩线鼠标笔记本电脑可爱女生有线礼品鼠标");
        silenceMouse.setMainImageUrl("http://img.alicdn.com/bao/uploaded/i4/3694365652/O1CN011rcf1bQn4jE1VKL_!!3694365652.jpg");
        silenceMouse.setCommodityDetailUrl("http://item.taobao.com/item.htm?id=578243398082");
        silenceMouse.setPrice(new BigDecimal("8.46"));
        silenceMouse.setSalesVolume(0);
        silenceMouse.setShortUrl("https://s.click.taobao.com/bdYNLJw");
        shoppingDTOList.add(silenceMouse);
        redisClientService.rightPush(RedisKeys.FIND_PAGE_SHOPPING_KEY, shoppingDTOList);
        List<Object> list = redisClientService.lRange(RedisKeys.FIND_PAGE_SHOPPING_KEY, 0L, 5L);
        Assert.assertNotNull(list);
        System.out.println(list);
    }

    @Test
    public void testRemove() {
        redisClientService.remove(RedisKeys.FIND_PAGE_SHOPPING_KEY);
        Object o = redisClientService.get(RedisKeys.FIND_PAGE_SHOPPING_KEY);
        Assert.assertNull(o);
    }

}
