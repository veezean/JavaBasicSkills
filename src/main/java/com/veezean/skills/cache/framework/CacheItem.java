package com.veezean.skills.cache.framework;

import lombok.Data;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * <类功能简要描述>
 *
 * @author 架构悟道
 * @since 2022/10/13
 */
@Data
public class CacheItem<V> {
    private V value;
    private long expireAt = -1L;
    // 后续有其它扩展，在此补充。。。

    public CacheItem(V value) {
        this.value = value;
    }

    public CacheItem(V value, long expireAt) {
        this.value = value;
        this.expireAt = expireAt;
    }

    public CacheItem(V value, int timeIntvl, TimeUnit timeUnit) {
        this.value = value;
        if (timeIntvl <= 0) {
            throw new RuntimeException("invalid expire time");
        }
        long expireAt = System.currentTimeMillis() + timeUnit.toMillis(timeIntvl);
        this.expireAt = expireAt;
    }
    public void setExpireAfter(int timeIntvl, TimeUnit timeUnit) {
        this.expireAt = System.currentTimeMillis() + timeUnit.toMillis(timeIntvl);
    }

    public boolean hasExpired() {
        return expireAt > 0L && System.currentTimeMillis() - expireAt > 0L;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CacheItem<?> cacheItem = (CacheItem<?>) o;
        return Objects.equals(value, cacheItem.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
