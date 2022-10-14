package com.veezean.skills.cache;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2022/10/13
 */
public class DemoCache<K, V> {
    private Map<K, CacheItem<V>> cache = new ConcurrentHashMap<>();

    public DemoCache() {
        timelyCleanExpiredItems();
    }

    /**
     * 单独设置某个key对应过期时间
     * @param key 唯一标识
     * @param timeIntvl 过期时间
     * @param timeUnit 时间单位
     */
    public void expireAfter(K key, int timeIntvl, TimeUnit timeUnit) {
        CacheItem<V> item = cache.get(key);
        if (item == null) {
            return;
        }
        long expireAt = System.currentTimeMillis() + timeUnit.toMillis(timeIntvl);
        item.setExpireAt(expireAt);
    }

    /**
     * 写入指定过期时间的缓存信息
     * @param key 唯一标识
     * @param value 缓存实际值
     * @param timeIntvl 过期时间
     * @param timeUnit 时间单位
     */
    public void put(K key, V value, int timeIntvl, TimeUnit timeUnit) {
        long expireAt = System.currentTimeMillis() + timeUnit.toMillis(timeIntvl);
        CacheItem<V> item = new CacheItem<>();
        item.setValue(value);
        item.setExpireAt(expireAt);
        cache.put(key, item);
    }

    /**
     * 从缓存中查询对应值
     * @param key 缓存key
     * @return 缓存值
     */
    public V get(K key) {
        CacheItem<V> item = cache.get(key);
        if (item == null) {
            return null;
        }
        if (item.hasExpired()) {
            cache.remove(key);
            return null;
        }
        return item.getValue();
    }

    private void timelyCleanExpiredItems() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                cache.entrySet().removeIf(entry -> entry.getValue().hasExpired());
            }
        }, 1000L, 1000L * 60 * 60 *24);
    }
}
