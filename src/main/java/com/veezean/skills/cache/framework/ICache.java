package com.veezean.skills.cache.framework;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 缓存容器接口
 *
 * @author 架构悟道
 * @since 2022/10/15
 */
public interface ICache<K, V> {
    Optional<V> get(K key);

    void put(K key, V value);

    void put(K key, V value, int timeIntvl, TimeUnit timeUnit);

    Optional<V> remove(K key);

    boolean containsKey(K key);

    void clear();

    Optional<Map<K, V>> getAll(Set<K> keys);

    void putAll(Map<K, V> map);

    void putAll(Map<K, V> map, int timeIntvl, TimeUnit timeUnit);

    boolean putIfAbsent(K key, V value);

    boolean putIfPresent(K key, V value);

    void expireAfter(K key, int timeIntvl, TimeUnit timeUnit);
}
