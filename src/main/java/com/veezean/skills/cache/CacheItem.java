package com.veezean.skills.cache;

import lombok.Data;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2022/10/13
 */
@Data
public class CacheItem<V> {
    private V value;
    private long expireAt;
    // 后续有其它扩展，在此补充。。。

    public boolean hasExpired() {
        return System.currentTimeMillis() - expireAt > 0L;
    }
}
