package com.veezean.skills.cache.framework;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2022/10/16
 */
public final class CacheFactory {
    public static ICache createCache(CacheType cacheType) {
        try {
            return cacheType.getClassType().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("failed to generate cache instance", e);
        }
    }
}
