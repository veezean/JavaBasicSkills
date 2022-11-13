package com.veezean.skills.cache.fwk;

import javax.cache.CacheManager;
import javax.cache.configuration.OptionalFeature;
import javax.cache.spi.CachingProvider;
import java.net.URI;
import java.util.Properties;

/**
 * <类功能简要描述>
 *
 * @author 架构悟道
 * @since 2022/10/15
 */
public class MyCachingProvider implements CachingProvider {

    private MemCacheManager memCacheManager;

    @Override
    public CacheManager getCacheManager(URI uri, ClassLoader classLoader, Properties properties) {
        return memCacheManager;
    }

    @Override
    public ClassLoader getDefaultClassLoader() {
        return getClass().getClassLoader();
    }

    @Override
    public URI getDefaultURI() {
        return URI.create(getClass().getCanonicalName());
    }

    @Override
    public Properties getDefaultProperties() {
        return null;
    }

    @Override
    public CacheManager getCacheManager(URI uri, ClassLoader classLoader) {
        return memCacheManager;
    }

    @Override
    public CacheManager getCacheManager() {
        return memCacheManager;
    }

    @Override
    public void close() {

    }

    @Override
    public void close(ClassLoader classLoader) {

    }

    @Override
    public void close(URI uri, ClassLoader classLoader) {

    }

    @Override
    public boolean isSupported(OptionalFeature optionalFeature) {
        return false;
    }
}
