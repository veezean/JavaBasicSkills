package com.veezean.skills.cache.fwk;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.configuration.CacheEntryListenerConfiguration;
import javax.cache.configuration.Configuration;
import javax.cache.integration.CompletionListener;
import javax.cache.processor.EntryProcessor;
import javax.cache.processor.EntryProcessorException;
import javax.cache.processor.EntryProcessorResult;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <类功能简要描述>
 *
 * @author 架构悟道
 * @since 2022/10/15
 */
public class MyCache<K, V> implements Cache<K, V> {

    private Map<K, V> cacheData;

    private CacheManager cacheManager;

    public MyCache(CacheManager cacheManager) {
        this.cacheData = new ConcurrentHashMap<>();
        this.cacheManager = cacheManager;
    }

    @Override
    public V get(K k) {
        if (isClosed()) {
            throw new IllegalStateException("cache has closed");
        }
        return cacheData.get(k);
    }

    @Override
    public Map<K, V> getAll(Set<? extends K> set) {
        if (isClosed()) {
            throw new IllegalStateException("cache has closed");
        }
        HashMap<K, V> map = new HashMap<>();
        if (set == null || set.isEmpty()) {
            return new HashMap<>();
        }
        for (K key : set) {
            V value = cacheData.get(key);
            if (value != null) {
                map.put(key, value);
            }
        }
        return map;
    }

    @Override
    public boolean containsKey(K key) {
        if (isClosed()) {
            throw new IllegalStateException("cache has closed");
        }
        return cacheData.containsKey(key);
    }

    @Override
    public void loadAll(Set<? extends K> set, boolean b, CompletionListener completionListener) {

    }

    @Override
    public void put(K k, V v) {
        if (isClosed()) {
            throw new IllegalStateException("cache has closed");
        }
        cacheData.put(k, v);
    }

    @Override
    public V getAndPut(K k, V v) {
        if (isClosed()) {
            throw new IllegalStateException("cache has closed");
        }
        V originalValue = cacheData.get(k);
        cacheData.put(k, v);
        return originalValue;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        if (isClosed()) {
            throw new IllegalStateException("cache has closed");
        }
        cacheData.putAll(map);
    }

    @Override
    public boolean putIfAbsent(K k, V v) {
        if (isClosed()) {
            throw new IllegalStateException("cache has closed");
        }
        if (cacheData.containsKey(k)) {
            return false;
        }
        cacheData.put(k, v);
        return true;
    }

    @Override
    public boolean remove(K k) {
        if (isClosed()) {
            throw new IllegalStateException("cache has closed");
        }
        if (cacheData.containsKey(k)) {
            cacheData.remove(k);
            return true;
        }
        return false;
    }

    @Override
    public boolean remove(K k, V v) {
        if (isClosed()) {
            throw new IllegalStateException("cache has closed");
        }
        if (cacheData.containsKey(k) && Objects.equals(cacheData.get(k), v)) {
            cacheData.remove(k);
            return true;
        }
        return false;
    }

    @Override
    public V getAndRemove(K k) {
        if (isClosed()) {
            throw new IllegalStateException("cache has closed");
        }
        V value = cacheData.get(k);
        cacheData.remove(k);
        return value;
    }

    @Override
    public boolean replace(K k, V v, V v1) {
        if (isClosed()) {
            throw new IllegalStateException("cache has closed");
        }
        V originalValue = cacheData.get(k);
        if (originalValue != null && originalValue.equals(v1)) {
            cacheData.put(k, v);
            return true;
        }
        return false;
    }

    @Override
    public boolean replace(K k, V v) {
        if (isClosed()) {
            throw new IllegalStateException("cache has closed");
        }
        if (cacheData.containsKey(k)) {
            cacheData.put(k, v);
            return true;
        }
        return false;
    }

    @Override
    public V getAndReplace(K k, V v) {
        if (isClosed()) {
            throw new IllegalStateException("cache has closed");
        }
        V originalValue = cacheData.get(k);
        cacheData.put(k, v);
        return originalValue;
    }

    @Override
    public void removeAll(Set<? extends K> set) {
        if (isClosed()) {
            throw new IllegalStateException("cache has closed");
        }
        if (set == null || set.isEmpty()) {
            return;
        }
        for (K key : set) {
            cacheData.remove(key);
        }
    }

    @Override
    public void removeAll() {
        if (isClosed()) {
            throw new IllegalStateException("cache has closed");
        }
        cacheData.clear();
    }

    @Override
    public void clear() {
        if (isClosed()) {
            throw new IllegalStateException("cache has closed");
        }
        cacheData.clear();
    }

    @Override
    public <C extends Configuration<K, V>> C getConfiguration(Class<C> aClass) {
        return null;
    }

    @Override
    public <T> T invoke(K k, EntryProcessor<K, V, T> entryProcessor, Object... objects) throws EntryProcessorException {
        return null;
    }

    @Override
    public <T> Map<K, EntryProcessorResult<T>> invokeAll(Set<? extends K> set, EntryProcessor<K, V, T> entryProcessor
            , Object... objects) {
        return null;
    }

    @Override
    public String getName() {
        return getClass().getCanonicalName();
    }

    @Override
    public CacheManager getCacheManager() {
        return cacheManager;
    }

    @Override
    public void close() {
        cacheData = null;
    }

    @Override
    public boolean isClosed() {
        return cacheData == null;
    }

    @Override
    public <T> T unwrap(Class<T> aClass) {
        return null;
    }

    @Override
    public void registerCacheEntryListener(CacheEntryListenerConfiguration<K, V> cacheEntryListenerConfiguration) {

    }

    @Override
    public void deregisterCacheEntryListener(CacheEntryListenerConfiguration<K, V> cacheEntryListenerConfiguration) {

    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return null;
    }
}
