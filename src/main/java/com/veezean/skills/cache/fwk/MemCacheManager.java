package com.veezean.skills.cache.fwk;

import lombok.SneakyThrows;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.configuration.Configuration;
import javax.cache.spi.CachingProvider;
import java.net.URI;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <类功能简要描述>
 *
 * @author 架构悟道
 * @since 2022/10/15
 */
public class MemCacheManager implements CacheManager {

    private CachingProvider cachingProvider;

    private ConcurrentHashMap<String, Cache> caches;

    public MemCacheManager(CachingProvider cachingProvider, ConcurrentHashMap<String, Cache> caches) {
        this.cachingProvider = cachingProvider;
        this.caches = caches;
    }

    @Override
    public CachingProvider getCachingProvider() {
        return this.cachingProvider;
    }

    @Override
    public URI getURI() {
        return URI.create(getClass().getCanonicalName());
    }

    @Override
    public ClassLoader getClassLoader() {
        return cachingProvider.getDefaultClassLoader();
    }

    @Override
    public Properties getProperties() {
        return null;
    }

    @Override
    public <K, V, C extends Configuration<K, V>> Cache<K, V> createCache(String s, C c) throws IllegalArgumentException {
       return caches.computeIfAbsent(s, v -> new MyCache<K, V>(this));
    }

    @Override
    public <K, V> Cache<K, V> getCache(String s, Class<K> aClass, Class<V> aClass1) {
        return caches.get(s);
    }

    @Override
    public <K, V> Cache<K, V> getCache(String s) {
        return caches.get(s);
    }

    @Override
    public Iterable<String> getCacheNames() {
        return caches.keySet();
    }

    @Override
    public void destroyCache(String s) {
        caches.remove(s);
    }

    @Override
    public void enableManagement(String s, boolean b) {

    }

    @Override
    public void enableStatistics(String s, boolean b) {

    }

    @Override
    public void close() {
        caches.clear();
    }

    @Override
    public boolean isClosed() {
        return false;
    }

    @Override
    public <T> T unwrap(Class<T> aClass) {
        try {
            return aClass.newInstance();
        } catch (Exception e) {
            return null;
        }
    }
}
