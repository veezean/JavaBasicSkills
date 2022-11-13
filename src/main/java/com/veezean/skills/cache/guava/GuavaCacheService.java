package com.veezean.skills.cache.guava;

import cn.hutool.core.util.RandomUtil;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableMap;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2022/11/6
 */
public class GuavaCacheService {

//    public LoadingCache<String, User> createCache() {
//        return CacheBuilder.newBuilder()
//                .refreshAfterWrite(1L, TimeUnit.SECONDS)
//                .build(new MyCacheLoader());
//    }

    public Cache<String, User> createCache() {
        return CacheBuilder.newBuilder().build();
    }

    public User queryUserFromDb(String userId) {
        return new User(userId, "test123");
    }

    private static class MyCacheLoader extends CacheLoader<String, User> {
        @Override
        public User load(String s) throws Exception {
            System.out.println(Thread.currentThread().getId() + "线程执行CacheLoader.load()...");
            Thread.sleep(500L);
            System.out.println(Thread.currentThread().getId() + "线程执行CacheLoader.load()结束...");
            return new User(s, RandomUtil.randomString(5));
        }

        @Override
        public ListenableFuture<User> reload(String key, User oldValue) throws Exception {
            System.out.println(Thread.currentThread().getId() + "线程执行CacheLoader.reload()，oldValue=" + oldValue);
            return super.reload(key, oldValue);
        }
    }

//    public static void main(String[] args) {
//        try {
//            LoadingCache<String, User> cache =
//                    CacheBuilder.newBuilder().refreshAfterWrite(1L, TimeUnit.SECONDS).build(new MyCacheLoader());
//            cache.put("123", new User("123", "ertyu"));
//            Thread.sleep(1100L);
//            Runnable task = () -> {
//                try {
//                    System.out.println(Thread.currentThread().getId() + "线程开始执行查询操作");
//                    User user = cache.get("123");
//                    System.out.println(Thread.currentThread().getId() + "线程查询结果：" + user);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            };
//
//            CompletableFuture.allOf(
//                    CompletableFuture.runAsync(task), CompletableFuture.runAsync(task)
//            ).thenRunAsync(task).join();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


    public static void main(String[] args) {
//        try {
//            GuavaCacheService cacheService = new GuavaCacheService();
//            Cache<String, User> cache = cacheService.createCache();
//            cache.put("123", new User("123", "123"));
//            cache.put("124", new User("124", "124"));
//            System.out.println(cache.getIfPresent("125"));
//            ImmutableMap<String, User> allPresentUsers =
//                    cache.getAllPresent(Stream.of("123", "124", "125").collect(Collectors.toList()));
//            System.out.println(allPresentUsers);
////            String userId = "123";
////            User user = cache.get(userId, () -> cacheService.queryUserFromDb(userId));
////            System.out.println("get对应用户信息：" + user);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        Cache<String, String> cache = CacheBuilder.newBuilder()
                .maximumSize(1)
                .removalListener(notification -> System.out.println(notification.getKey() + "被移除，原因：" + notification.getCause()))
                .build();
        cache.put("key1", "value1");
        System.out.println("key1写入后，当前缓存内的keys：" + cache.asMap().keySet());
        cache.put("key2", "value1");
        System.out.println("key2写入后，当前缓存内的keys：" + cache.asMap().keySet());
    }
}
