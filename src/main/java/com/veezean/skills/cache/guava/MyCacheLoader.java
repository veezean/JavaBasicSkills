package com.veezean.skills.cache.guava;

import com.google.common.cache.CacheLoader;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2022/10/31
 */
public class MyCacheLoader extends CacheLoader<String, User> {

    @Override
    public User load(String key) throws Exception {
        return null;
    }


}
