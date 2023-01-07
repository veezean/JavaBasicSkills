package com.veezean.skills.cache.fwk;

import javax.cache.configuration.Configuration;

/**
 * <类功能简要描述>
 *
 * @author 架构悟道
 * @since 2022/10/15
 */
public class MyConfiguration<K,V> implements Configuration<K, V> {

    @Override
    public Class<K> getKeyType() {
        return null;
    }

    @Override
    public Class<V> getValueType() {
        return null;
    }

    @Override
    public boolean isStoreByValue() {
        return false;
    }
}
