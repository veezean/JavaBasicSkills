package com.veezean.skills.cache.framework;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <类功能简要描述>
 *
 * @author 架构悟道
 * @since 2022/10/15
 */
@AllArgsConstructor
@Getter
public enum CacheType {
    DEFAULT(DefaultCache.class),
    LRU(LruCache.class);
    private Class<? extends ICache> classType;
}
