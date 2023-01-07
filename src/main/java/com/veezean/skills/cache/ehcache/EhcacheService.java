package com.veezean.skills.cache.ehcache;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2022/11/18
 */
public class EhcacheService {

    public static void main(String[] args) {
        CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                .withCache("myCache", CacheConfigurationBuilder.newCacheConfigurationBuilder(Integer.class,
                        String.class,
                        ResourcePoolsBuilder.newResourcePoolsBuilder()
                                .heap(1, MemoryUnit.MB)
                                .disk(10, MemoryUnit.GB, true)) // 指定需要持久化到磁盘
                        .build())
                .with(CacheManagerBuilder.persistence("d:\\myCache\\")) // 指定持久化磁盘路径
                .build(true);
        Cache<Integer, String> myCache = cacheManager.getCache("myCache", Integer.class, String.class);
        System.out.println(myCache.getClass().getCanonicalName());
        myCache.put(1, "value1");
        myCache.put(2, "value2");
        System.out.println(myCache.get(2));
        cacheManager.close();
    }
}
