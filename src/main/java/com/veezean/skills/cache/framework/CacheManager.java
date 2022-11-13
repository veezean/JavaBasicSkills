package com.veezean.skills.cache.framework;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <类功能简要描述>
 *
 * @author 架构悟道
 * @since 2022/10/15
 */
public class CacheManager implements ICacheManager {
    private Map<String, ICache> caches = new ConcurrentHashMap<>();
    private List<ICacheHandler> handlers = Collections.synchronizedList(new ArrayList<>());

    public CacheManager() {
        timelyCleanExpiredItems();
    }

    @Override
    public void createCache(String key, CacheType cacheType) {
        ICache cache = CacheFactory.createCache(cacheType);
        caches.put(key, cache);
    }

    @Override
    public void destoryCache(String key) {
        caches.remove(key);
    }

    @Override
    public void destoryAllCache() {
        caches.clear();
    }

    @Override
    public Set<String> getAllCacheNames() {
        return caches.keySet();
    }

    @Override
    public <K, V> ICache<K, V> getCache(String cacheCollectionKey, Class<K> keyType, Class<V> valueType) {
        try {
            return (ICache<K, V>) caches.get(cacheCollectionKey);
        } catch (Exception e) {
            throw new RuntimeException("failed to get cache", e);
        }

    }

    private void timelyCleanExpiredItems() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("start clean expired data timely");
                handlers.forEach(ICacheHandler::clearAllExpiredCaches);
            }
        }, 60000L, 1000L * 60 * 60 * 24);
    }

}
