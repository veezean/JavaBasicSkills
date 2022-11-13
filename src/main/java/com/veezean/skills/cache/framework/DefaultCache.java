package com.veezean.skills.cache.framework;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <类功能简要描述>
 *
 * @author 架构悟道
 * @since 2022/10/15
 */
public class DefaultCache<K, V> implements ICache<K, V>, ICacheHandler<K> {
    private Map<K, CacheItem<V>> data = new ConcurrentHashMap<>();
    @Override
    public Optional<V> get(K key) {
        removeIfExpired(key);
        return Optional.ofNullable(data.get(key)).map(CacheItem::getValue);
    }

    @Override
    public void put(K key, V value) {
        data.put(key, new CacheItem<>(value));
    }

    @Override
    public void put(K key, V value, int timeIntvl, TimeUnit timeUnit) {
        data.put(key, new CacheItem<>(value, timeIntvl, timeUnit));
    }

    @Override
    public Optional<V> remove(K key) {
        removeIfExpired(key);
        return Optional.ofNullable(data.remove(key)).map(CacheItem::getValue);
    }

    @Override
    public boolean containsKey(K key) {
        removeIfExpired(key);
        return data.containsKey(key);
    }

    @Override
    public void clear() {
        data.clear();
    }

    @Override
    public Optional<Map<K, V>> getAll(Set<K> keys) {
        if (keys == null || keys.isEmpty()) {
            return Optional.empty();
        }
        Map<K, V> map = new HashMap<>();
        for (K key : keys) {
            removeIfExpired(key);
            Optional.ofNullable(data.get(key)).map(CacheItem::getValue)
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
            data.put(k, new CacheItem<>(v));
        });
    }

    @Override
    public void putAll(Map<K, V> map, int timeIntvl, TimeUnit timeUnit) {
        if (map == null || map.isEmpty()) {
            return;
        }
        map.forEach((k, v) -> {
            data.put(k, new CacheItem<>(v, timeIntvl, timeUnit));
        });
    }

    @Override
    public boolean putIfAbsent(K key, V value) {
        removeIfExpired(key);
        if (!data.containsKey(key)) {
            data.put(key, new CacheItem<>(value));
            return true;
        }
        return false;
    }

    @Override
    public boolean putIfPresent(K key, V value) {
        removeIfExpired(key);
        if (data.containsKey(key)) {
            data.put(key, new CacheItem<>(value));
            return true;
        }
        return false;
    }

    @Override
    public void expireAfter(K key, int timeIntvl, TimeUnit timeUnit) {
        removeIfExpired(key);
        CacheItem<V> cacheItem = data.get(key);
        if (cacheItem == null) {
            return;
        }
        cacheItem.setExpireAfter(timeIntvl, timeUnit);
    }

    @Override
    public synchronized void removeIfExpired(K key) {
        Optional.ofNullable(data.get(key)).map(CacheItem::hasExpired).ifPresent(expired -> {
            if (expired) {
                data.remove(key);
            }
        });
    }

    @Override
    public synchronized void clearAllExpiredCaches() {
        List<K> expiredKeys = data.entrySet().stream()
                .filter(cacheItemEntry -> cacheItemEntry.getValue().hasExpired())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        for (K key : expiredKeys) {
            data.remove(key);
        }
    }
}
