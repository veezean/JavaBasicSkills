package com.veezean.skills.cache.fwk;

import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;

/**
 * <类功能简要描述>
 *
 * @author 架构悟道
 * @since 2022/10/14
 */
public class Main {

    public static void main(String[] args) {
        CachingProvider provider =  Caching.getCachingProvider();
        CacheManager cacheManager = provider.getCacheManager();
        System.out.println(provider);
        System.out.println(cacheManager);
    }

}
