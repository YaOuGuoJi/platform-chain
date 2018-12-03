package com.bester.platform.platformchain.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @author liuwen
 * @date 2018/11/30
 */
public interface RedisClientService {

    /**
     * 存放对象
     *
     * @param key
     * @param value
     */
    void set(String key, Object value);

    /**
     * 存放对象，设置有效期限
     *
     * @param key
     * @param value
     * @param expireTime 过期时间段 单位：毫秒
     */
    void set(String key, Object value, Long expireTime);

    /**
     * 存放对象，设置过期时间点
     *
     * @param key
     * @param value
     * @param expireDate
     */
    void set(String key, Object value, Date expireDate);

    /**
     * 获取对象
     *
     * @param key
     * @return
     */
    Object get(String key);

    /**
     * 列表插入
     *
     * @param key
     * @param value
     * @return
     */
    void rightPush(String key, Object value);

    /**
     * 列表批量插入
     *
     * @param key
     * @param values
     */
    void rightPush(String key, Collection<Object> values);

    /**
     * 列表获取
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    List<Object> lRange(String key, long start, long end);

    /**
     * 判断是否存在key
     *
     * @param key
     * @return
     */
    Boolean exists(String key);

    /**
     * 删除key
     *
     * @param key
     */
    void remove(String key);

}
