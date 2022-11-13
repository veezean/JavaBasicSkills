package com.veezean.skills.cache.framework;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <类功能简要描述>
 *
 * @author 架构悟道
 * @since 2022/10/15
 */
public class LruHashMap<K, V> extends LinkedHashMap<K, V> {
    private static final long serialVersionUID = 1287190405215174569L;
    private int maxEntries;

    public LruHashMap(int maxEntries, boolean accessOrder) {
        super(16, 0.75f, accessOrder);
        this.maxEntries = maxEntries;
    }

    /**
     *  自定义数据淘汰触发条件，在每次put操作的时候会调用此方法来判断下
     */
    protected synchronized boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > maxEntries;
    }

    @Override
    public synchronized V get(Object key) {
        return super.get(key);
    }

    @Override
    public synchronized void clear() {
        super.clear();
    }

    @Override
    public synchronized V put(K key, V value) {
        return super.put(key, value);
    }

    @Override
    public synchronized void putAll(Map<? extends K, ? extends V> m) {
        super.putAll(m);
    }

    @Override
    public synchronized V remove(Object key) {
        return super.remove(key);
    }

    @Override
    public synchronized boolean containsKey(Object key) {
        return super.containsKey(key);
    }

    @Override
    public synchronized boolean containsValue(Object value) {
        return super.containsValue(value);
    }
}
