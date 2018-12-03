package com.bester.platform.platformchain.impl;

import com.bester.platform.platformchain.service.RedisClientService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author liuwen
 * @date 2018/11/30
 */
@Service
public class RedisClientServiceImpl<K, V> implements RedisClientService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void set(String key, Object value) {
        Assert.isTrue(StringUtils.isNotEmpty(key), "key不得为空！");
        Assert.isTrue(value instanceof Serializable, "value对象必须实现序列化接口！");
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, value);
    }

    @Override
    public void set(String key, Object value, Long expireTime) {
        Assert.isTrue(StringUtils.isNotEmpty(key), "key不得为空！");
        Assert.isTrue(value instanceof Serializable, "value对象必须实现序列化接口！");
        Assert.isTrue(expireTime > 0, "过期时间必须大于0！");
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, value);
        redisTemplate.expire(key, expireTime, TimeUnit.MILLISECONDS);
    }

    @Override
    public void set(String key, Object value, Date expireDate) {
        Assert.isTrue(StringUtils.isNotEmpty(key), "key不得为空！");
        Assert.isTrue(value instanceof Serializable, "value对象必须实现序列化接口！");
        Assert.isTrue(expireDate.after(new Date()), "过期时间必须大于0！");
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, value);
        redisTemplate.expireAt(key, expireDate);
    }

    @Override
    public Object get(String key) {
        Assert.isTrue(StringUtils.isNotEmpty(key), "key不得为空！");
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(key);
    }

    @Override
    public void rightPush(String key, Object value) {
        Assert.isTrue(StringUtils.isNotEmpty(key), "key不得为空！");
        Assert.isTrue(value instanceof Serializable, "value对象必须实现序列化接口！");
        ListOperations<String, Object> listOperations = redisTemplate.opsForList();
        listOperations.rightPush(key, value);
    }

    @Override
    public void rightPush(String key, Object... values) {
        Assert.isTrue(StringUtils.isNotEmpty(key), "key不得为空！");
        Assert.isTrue(values != null, "value不得为空！");
        ListOperations<String, Object> listOperations = redisTemplate.opsForList();
        listOperations.rightPushAll(key, values);
    }

    @Override
    public List<Object> lRange(String key, long start, long end) {
        Assert.isTrue(StringUtils.isNotEmpty(key), "key不得为空！");
        Assert.isTrue(start <= end, "start参数不得大于end参数！");
        ListOperations<String, Object> listOperations = redisTemplate.opsForList();
        return listOperations.range(key, start, end);
    }

    @Override
    public Boolean exists(String key) {
        Assert.isTrue(StringUtils.isNotEmpty(key), "key不得为空！");
        return redisTemplate.hasKey(key);
    }

    @Override
    public void remove(String key) {
        Assert.isTrue(StringUtils.isNotEmpty(key), "key不得为空！");
        Boolean exists = redisTemplate.hasKey(key);
        if (exists != null && exists) {
            redisTemplate.delete(key);
        }
    }
}
