package com.veezean.skills.cache.guava;

import com.google.common.cache.*;

import java.lang.instrument.Instrumentation;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2022/10/30
 */
public class CacheService {

    Instrumentation instrumentation = null;

    UserDao userDao = new UserDao();

    public void createCache() {
        Cache<String, User> cache = CacheBuilder.newBuilder()
                .initialCapacity(100)
                .maximumSize(10000)
                .concurrencyLevel(4)
                .expireAfterAccess(10L, TimeUnit.MINUTES)
                .expireAfterWrite(10L, TimeUnit.MINUTES)
                .weigher((key, value) -> {
                    return 1;
                })
                .maximumWeight(10L)
                .refreshAfterWrite(1L, TimeUnit.MINUTES)
                .recordStats()
                .build();

        User ifPresent = cache.getIfPresent("12");

    }

    public Cache<String, User> createUserCache() {
        return CacheBuilder.newBuilder().expireAfterWrite(30L, TimeUnit.MINUTES).build();
    }


    public Cache<String, User> createUserCache2() {
        return CacheBuilder.newBuilder().expireAfterAccess(30L, TimeUnit.MINUTES).build();
    }

    public Cache<String, User> createUserCache3() {
        return CacheBuilder.newBuilder()
                .maximumSize(10000L)
                .build();
    }

    public Cache<String, User> createUserCache4() {
        return CacheBuilder.newBuilder()
                .maximumWeight(10000L)
                .weigher((key, value) -> (int) Math.ceil(instrumentation.getObjectSize(value) / 1024L))
                .build();
    }


    public LoadingCache<String, User> createUserCache5() {
        return CacheBuilder.newBuilder()
                .recordStats()
                .build(new CacheLoader<String, User>() {
                    @Override
                    public User load(String key) throws Exception {
                        System.out.println(key + "用户缓存不存在，尝试CacheLoader回源查找并回填...");
                        User user = userDao.getUser(key);
                        if (user == null) {
                            System.out.println(key + "用户不存在");
                        }
                        return user;
                    }
                });
    }

    public LoadingCache<String, User> createUserCache6() {
        return CacheBuilder.newBuilder()
                .initialCapacity(1000) // 初始容量
                .maximumSize(10000L)   // 设定最大容量
                .expireAfterWrite(30L, TimeUnit.MINUTES) // 设定写入过期时间
                .concurrencyLevel(8)  // 设置最大并发写操作线程数
                .refreshAfterWrite(1L, TimeUnit.MINUTES) // 设定自动刷新数据时间
                .recordStats() // 开启缓存执行情况统计
                .build(new CacheLoader<String, User>() {
                    @Override
                    public User load(String key) throws Exception {
                        return userDao.getUser(key);
                    }
                });
    }


    public Cache<String, User> createUserCache7() {
        return CacheBuilder.newBuilder()
                .initialCapacity(1000) // 初始容量
                .maximumSize(10000L)   // 设定最大容量
                .expireAfterWrite(30L, TimeUnit.MINUTES) // 设定写入过期时间
                .concurrencyLevel(8)  // 设置最大并发写操作线程数
                .refreshAfterWrite(1L, TimeUnit.MINUTES) // 设定自动刷新数据时间
                .recordStats() // 开启缓存执行情况统计
                .build();
    }

    public User findUser(Cache<String, User> cache, String userId) {
        try {
            return cache.get(userId, () -> {
                System.out.println(userId + "用户缓存不存在，尝试回源查找并回填...");
                User user = userDao.getUser(userId);
                if (user == null) {
                    System.out.println(userId + "用户不存在");
                }
                return user;
            });
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
//        Cache<String, User> cache = CacheBuilder.newBuilder().build();

        UserDao userDao = new UserDao();

        CacheService cacheService = new CacheService();
//        LoadingCache<String, User> cache = CacheBuilder.newBuilder()
//                .recordStats()
//                .build(new CacheLoader<String, User>() {
//                    @Override
//                    public User load(String key) throws Exception {
//                        System.out.println(key + "用户缓存不存在，尝试CacheLoader回源查找并回填...");
//                        User user = userDao.getUser(key);
//                        if (user == null) {
//                            System.out.println(key + "用户不存在");
//                        }
//                        return user;
//                    }
//                });

//        LoadingCache<String, User> cache = cacheService.createUserCache6();
//
//        cache.put("122", new User("122"));
//        System.out.println("put操作后查询：" + cache.getIfPresent("122"));
//        cache.invalidate("122");
//        System.out.println("invalidate操作后查询：" + cache.getIfPresent("122"));
//        System.out.println(cache.stats());

//        Cache<String, User> userCache7 = cacheService.createUserCache7();
//        try {
//            User xxx = userCache7.get("123", () -> {
//                return new User("xxx");
//            });
//            System.out.println(xxx);
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }

//        try {
//            Cache<String, String> cache = CacheBuilder.newBuilder()
//                    .maximumSize(2)
//                    .expireAfterWrite(1L, TimeUnit.SECONDS)
//                    .removalListener(notification -> {
//                        System.out.println("---监听到缓存移除事件：" + notification);
//                    })
//                    .recordStats()
//                    .build();
//            System.out.println("Cache类型：" + cache.getClass().getName());
//            System.out.println("put放入key1");
//            cache.put("key1", "value1");
//            System.out.println("put放入key2");
//            cache.put("key2", "value2");
//            System.out.println("put放入key3");
//            cache.put("key3", "value3");
//            cache.put("key4", "value1");
//            System.out.println("put操作后，当前缓存记录数：" + cache.size());
//            System.out.println("查询key1对应值：" + cache.getIfPresent("key1"));
//            System.out.println("统计信息：" + cache.stats());

//            System.out.println("-------sleep 等待超过过期时间-------");
//            Thread.sleep(1100L);

//            cache.put("key4", "value4");
//
//            System.out.println("当前缓存记录数：" + cache.size());
//            System.out.println("当前统计信息：" + cache.stats());
//
//            cache.put("key3", "value3");
//            System.out.println("put key3操作执行结束");
//            System.out.println("当前缓存记录数：" + cache.size());
//            System.out.println("当前统计信息：" + cache.stats());
//
//            System.out.println("执行key1查询操作：" + cache.getIfPresent("key1"));
//            System.out.println("当前缓存记录数：" + cache.size());
//            System.out.println("当前统计信息：" + cache.stats());
//            System.out.println("剩余数据信息：" + cache.asMap());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        try {
//            Cache<String, String> cache = CacheBuilder.newBuilder().weigher((key, value) -> 2)
//                    .maximumSize(2)
//                    .build();
//            cache.put("key1", "value1");
//            cache.put("key2", "value2");
//            System.out.println(cache.size());

            int segmentCount = 1;
            while (segmentCount < 17) {
                segmentCount <<= 1;
            }
            System.out.println(segmentCount);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
