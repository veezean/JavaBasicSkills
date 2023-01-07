package com.veezean.skills.cache.framework;

import java.util.Set;

/**
 * <类功能简要描述>
 *
 * @author Veezean
 * @since 2022/10/16
 */
public interface ICacheManager {
    <K, V> ICache<K, V> getCache(String key, Class<K> keyType, Class<V> valueType);
    void createCache(String key, CacheType cacheType);
    void destoryCache(String key);
    void destoryAllCache();
    Set<String> getAllCacheNames();
}
