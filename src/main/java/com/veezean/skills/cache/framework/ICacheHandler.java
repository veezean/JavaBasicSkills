package com.veezean.skills.cache.framework;

/**
 * <类功能简要描述>
 *
 * @author Veezean
 * @since 2022/10/16
 */
public interface ICacheHandler<K> {
    void removeIfExpired(K key);
    void clearAllExpiredCaches();
}
