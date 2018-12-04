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
        ShoppingDTO cherry80 = new ShoppingDTO();
        cherry80.setCommodityId(35194478440L);
        cherry80.setCommodityName("CHERRY樱桃G80-3850 MX3.0游戏电竞机械键盘黑轴青轴茶轴红轴台式");
        cherry80.setMainImageUrl("http://img.alicdn.com/bao/uploaded/i4/1762621111/O1CN01kFUkmh1K4sVKD7Suw_!!0-item_pic.jpg");
        cherry80.setCommodityDetailUrl("http://item.taobao.com/item.htm?id=35194478440");
        cherry80.setPrice(new BigDecimal("499.00"));
        cherry80.setSalesVolume(47);
        cherry80.setShortUrl("https://s.click.taobao.com/5QIjHJw");
        ShoppingDTO razerKeyboardX = new ShoppingDTO();
        razerKeyboardX.setCommodityId(38214919782L);
        razerKeyboardX.setCommodityName("Razer雷蛇 黑寡妇蜘蛛幻彩X竞技游戏机械键盘青轴电竞吃鸡RGB背光");
        razerKeyboardX.setMainImageUrl("http://img.alicdn.com/bao/uploaded/i2/1085315961/O1CN01kp62VH1tuBQKWUxCp_!!0-item_pic.jpg");
        razerKeyboardX.setCommodityDetailUrl("http://item.taobao.com/item.htm?id=38214919782");
        razerKeyboardX.setPrice(new BigDecimal("369.00"));
        razerKeyboardX.setSalesVolume(1136);
        razerKeyboardX.setShortUrl("https://s.click.taobao.com/hfIjHJw");
        ShoppingDTO usbMouse = new ShoppingDTO();
        usbMouse.setCommodityId(541906300193L);
        usbMouse.setCommodityName("金河田穴手笔记本电脑USB有线游戏鼠标暖手发热鼠标");
        usbMouse.setMainImageUrl("http://img.alicdn.com/bao/uploaded/i2/TB1GJRKOpXXXXX9XXXXXXXXXXXX_!!0-item_pic.jpg");
        usbMouse.setCommodityDetailUrl("http://item.taobao.com/item.htm?id=541906300193");
        usbMouse.setPrice(new BigDecimal("49.00"));
        usbMouse.setSalesVolume(11);
        usbMouse.setShortUrl("https://s.click.taobao.com/QLDjHJw");
        ShoppingDTO logitech = new ShoppingDTO();
        logitech.setCommodityId(544515024885L);
        logitech.setCommodityName("Logitech/罗技G213键盘G102电竞鼠标RGB炫光有线键鼠套装");
        logitech.setMainImageUrl("http://img.alicdn.com/bao/uploaded/i4/1652528654/O1CN01BUvvwK2Dna2B7nbJl_!!0-item_pic.jpg");
        logitech.setCommodityDetailUrl("http://item.taobao.com/item.htm?id=544515024885");
        logitech.setPrice(new BigDecimal("399.00"));
        logitech.setSalesVolume(85);
        logitech.setShortUrl("https://s.click.taobao.com/bMFjHJw");
        ShoppingDTO razerKeyboard = new ShoppingDTO();
        razerKeyboard.setCommodityId(561023476331L);
        razerKeyboard.setCommodityName("Razer雷蛇黑寡妇机械键盘蜘蛛x竞技幻彩版电竞吃鸡游戏cherry樱桃轴青轴有线RGB背光机械键盘");
        razerKeyboard.setMainImageUrl("http://img.alicdn.com/bao/uploaded/i3/3488300717/O1CN01hCzDXz1HAQSFVosOr_!!0-item_pic.jpg");
        razerKeyboard.setCommodityDetailUrl("http://item.taobao.com/item.htm?id=561023476331");
        razerKeyboard.setPrice(new BigDecimal("349.00"));
        razerKeyboard.setSalesVolume(111);
        razerKeyboard.setShortUrl("https://s.click.taobao.com/mPHjHJw");
        ShoppingDTO akko = new ShoppingDTO();
        akko.setCommodityId(574513821124L);
        akko.setCommodityName("Akko3108DS Ducky游戏机械键盘Cherry樱桃黑红轴茶轴Pbt吃鸡电竞");
        akko.setMainImageUrl("http://img.alicdn.com/bao/uploaded/i4/2097616571/O1CN01J5DFhg1yPZ1WddqQ7_!!0-item_pic.jpg");
        akko.setCommodityDetailUrl("http://item.taobao.com/item.htm?id=574513821124");
        akko.setPrice(new BigDecimal("349.00"));
        akko.setSalesVolume(91);
        akko.setShortUrl("https://s.click.taobao.com/EfHjHJw");
        ShoppingDTO shoppingDTO = new ShoppingDTO();
        shoppingDTO.setCommodityId(574867290284L);
        shoppingDTO.setCommodityName("有线电脑鼠标家用办公常用USB电竞游戏鼠标台式牧马人 9.9包邮");
        shoppingDTO.setMainImageUrl("http://img.alicdn.com/bao/uploaded/i4/3670180162/O1CN011D4EbqhPVtQE5bU_!!3670180162.jpg");
        shoppingDTO.setCommodityDetailUrl("http://item.taobao.com/item.htm?id=574867290284");
        shoppingDTO.setPrice(new BigDecimal("3.56"));
        shoppingDTO.setSalesVolume(0);
        shoppingDTO.setShortUrl("https://s.click.taobao.com/cdYNLJw");
        ShoppingDTO gameMouse = new ShoppingDTO();
        gameMouse.setCommodityId(577949864722L);
        gameMouse.setCommodityName("索魂F8游戏有线鼠标电竞专业家用办公笔记本台式电脑USB鼠标");
        gameMouse.setMainImageUrl("http://img.alicdn.com/bao/uploaded/i4/3694365652/O1CN011rcf1b3cXwLqjY9_!!3694365652.jpg");
        gameMouse.setCommodityDetailUrl("http://item.taobao.com/item.htm?id=577949864722");
        gameMouse.setPrice(new BigDecimal("5.80"));
        gameMouse.setSalesVolume(0);
        gameMouse.setShortUrl("https://s.click.taobao.com/2EfNLJw");
        ShoppingDTO m100Mouse = new ShoppingDTO();
        m100Mouse.setCommodityId(577950148590L);
        m100Mouse.setCommodityName("/M100R 鼠标有线 电脑台式机笔记本专用外设光电鼠标");
        m100Mouse.setMainImageUrl("http://img.alicdn.com/bao/uploaded/i4/3694365652/O1CN011rcf1ZJm0WjT96k_!!3694365652.jpg");
        m100Mouse.setCommodityDetailUrl("http://item.taobao.com/item.htm?id=577950148590");
        m100Mouse.setPrice(new BigDecimal("18.68"));
        m100Mouse.setSalesVolume(0);
        m100Mouse.setShortUrl("https://s.click.taobao.com/SycNLJw");
        ShoppingDTO fm100Mouse = new ShoppingDTO();
        fm100Mouse.setCommodityId(578122737337L);
        fm100Mouse.setCommodityName("包邮HP惠普FM100有线鼠标笔记本台式电脑USB接口通用办公游戏鼠标");
        fm100Mouse.setMainImageUrl("http://img.alicdn.com/bao/uploaded/i3/3694365652/O1CN011rcf1Zggm94koxA_!!3694365652.jpg");
        fm100Mouse.setCommodityDetailUrl("http://item.taobao.com/item.htm?id=578122737337");
        fm100Mouse.setPrice(new BigDecimal("5.31"));
        fm100Mouse.setSalesVolume(1);
        fm100Mouse.setShortUrl("https://s.click.taobao.com/PzgNLJw");
        ShoppingDTO silenceMouse = new ShoppingDTO();
        silenceMouse.setCommodityId(578243398082L);
        silenceMouse.setCommodityName("无声静音卡通伸缩线鼠标笔记本电脑可爱女生有线礼品鼠标");
        silenceMouse.setMainImageUrl("http://img.alicdn.com/bao/uploaded/i4/3694365652/O1CN011rcf1bQn4jE1VKL_!!3694365652.jpg");
        silenceMouse.setCommodityDetailUrl("http://item.taobao.com/item.htm?id=578243398082");
        silenceMouse.setPrice(new BigDecimal("8.46"));
        silenceMouse.setSalesVolume(0);
        silenceMouse.setShortUrl("https://s.click.taobao.com/bdYNLJw");
        redisClientService.rightPush(RedisKeys.FIND_PAGE_SHOPPING_KEY, shoppingDTO, gameMouse, m100Mouse, fm100Mouse, silenceMouse, cherry80, razerKeyboard, razerKeyboardX, usbMouse, logitech, akko);
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
