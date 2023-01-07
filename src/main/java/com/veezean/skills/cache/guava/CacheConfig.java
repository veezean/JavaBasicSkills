//package com.veezean.skills.cache.guava;
//
//import com.google.common.cache.CacheBuilder;
//import org.springframework.cache.CacheManager;
//import org.springframework.cache.support.SimpleCacheManager;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.concurrent.TimeUnit;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
///**
// * <类功能简要描述>
// *
// * @author Wang Weiren
// * @since 2022/11/6
// */
//@Configuration
//public class CacheConfig {
//    @Bean
//    public CacheManager cacheManager() {
////        Caff
////        SimpleCacheManager cacheManager = new SimpleCacheManager();
////        GuavaCache guavaCache = new GuavaCache();
////        cacheManager.setCaches(Stream.of(CacheBuilder.newBuilder().refreshAfterWrite(10L, TimeUnit.MINUTES).build()).collect(Collectors.toList()));
//    }
//}
