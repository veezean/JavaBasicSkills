package com.veezean.skills.cache.framework;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <类功能简要描述>
 *
 * @author 架构悟道
 * @since 2022/10/15
 */
public class LruCache<K, V> implements ICache<K, V>, ICacheHandler<K> {

    private LruHashMap<K, CacheItem<V>> lruHashMap = new LruHashMap(10000, true);
    @Override
    public Optional<V> get(K key) {
        removeIfExpired(key);
        return Optional.ofNullable(lruHashMap.get(key)).map(CacheItem::getValue);
    }

    @Override
    public void put(K key, V value) {
        lruHashMap.put(key, new CacheItem<>(value));
    }

    @Override
    public void put(K key, V value, int timeIntvl, TimeUnit timeUnit) {
        lruHashMap.put(key, new CacheItem<>(value, timeIntvl, timeUnit));
    }

    @Override
    public Optional<V> remove(K key) {
        removeIfExpired(key);
        return Optional.ofNullable(lruHashMap.remove(key)).map(CacheItem::getValue);
    }

    @Override
    public boolean containsKey(K key) {
        removeIfExpired(key);
        return lruHashMap.containsKey(key);
    }

    @Override
    public void clear() {
        lruHashMap.clear();
    }

    @Override
    public Optional<Map<K, V>> getAll(Set<K> keys) {
        if (keys == null || keys.isEmpty()) {
            return Optional.empty();
        }
        Map<K, V> map = new HashMap<>();
        for (K key : keys) {
            removeIfExpired(key);
            Optional.ofNullable(lruHashMap.get(key)).map(CacheItem::getValue)
                    .ifPresent(value -> map.put(key, value));
        }
        return Optional.of(map);
    }

    @Override
    public void putAll(Map<K, V> map) {
        if (map == null || map.isEmpty()) {
            return;
        }
        map.forEach((k, v) -> {
            lruHashMap.put(k, new CacheItem<>(v));
        });
    }

    @Override
    public void putAll(Map<K, V> map, int timeIntvl, TimeUnit timeUnit) {
        if (map == null || map.isEmpty()) {
            return;
        }
        map.forEach((k, v) -> {
            lruHashMap.put(k, new CacheItem<>(v, timeIntvl, timeUnit));
        });
    }

    @Override
    public boolean putIfAbsent(K key, V value) {
        removeIfExpired(key);
        if (!lruHashMap.containsKey(key)) {
            lruHashMap.put(key, new CacheItem<>(value));
            return true;
        }
        return false;
    }

    @Override
    public boolean putIfPresent(K key, V value) {
        removeIfExpired(key);
        if (lruHashMap.containsKey(key)) {
            lruHashMap.put(key, new CacheItem<>(value));
            return true;
        }
        return false;
    }

    @Override
    public void expireAfter(K key, int timeIntvl, TimeUnit timeUnit) {
        removeIfExpired(key);
        CacheItem<V> cacheItem = lruHashMap.get(key);
        if (cacheItem == null) {
            return;
        }
        cacheItem.setExpireAfter(timeIntvl, timeUnit);
    }

    @Override
    public synchronized void removeIfExpired(K key) {
        Optional.ofNullable(lruHashMap.get(key)).map(CacheItem::hasExpired).ifPresent(expired -> {
            if (expired) {
                lruHashMap.remove(key);
            }
        });
    }

    @Override
    public synchronized void clearAllExpiredCaches() {
        List<K> expiredKeys = lruHashMap.entrySet().stream()
                .filter(cacheItemEntry -> cacheItemEntry.getValue().hasExpired())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        for (K key : expiredKeys) {
            lruHashMap.remove(key);
        }
    }
}
