package com.veezean.skills.cache.caffeine;

import cn.hutool.core.util.RandomUtil;
import com.github.benmanes.caffeine.cache.*;
import com.veezean.skills.cache.guava.User;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2022/11/9
 */
public class CaffeineCacheService {

    private static UserDao userDao = new UserDao();
    private static DepartmentDao departmentDao = new DepartmentDao();

    public LoadingCache<String, User> createUserCache() {
        return Caffeine.newBuilder()
                .initialCapacity(1000)
                .maximumSize(10000L)
                .expireAfterWrite(30L, TimeUnit.MINUTES)
//                .concurrencyLevel(8)
                .recordStats()
                .build(key -> userDao.getUser(key));
    }

    public Cache<String, String> createCache() {
        Cache<String, String> cache = Caffeine.newBuilder()
                .executor(Executors.newFixedThreadPool(10))
                .expireAfterWrite(100L, TimeUnit.MILLISECONDS)
                .scheduler((executor, command, delay, unit) -> {
                    System.out.println("scheduler executed...");
                    System.out.println("command=" + command);
                    System.out.println("delay=" + delay);
                    System.out.println("unit=" + unit);
                    return null;
                })
                .evictionListener((key, value, cause) -> {
                    System.out.println(key + "被删除，原因：" + cause);
                })
//                .expireAfter(new Expiry<String, String>() {
//
//                    @Override
//                    public long expireAfterCreate(String key, String value, long currentTime) {
//                        return TimeUnit.SECONDS.toNanos(1);
//                    }
//
//                    @Override
//                    public long expireAfterUpdate(String key, String value, long currentTime,
//                                                  @NonNegative long currentDuration) {
//                        System.out.println("currentDuration=" + currentDuration);
//                        return currentDuration;
//                    }
//
//                    @Override
//                    public long expireAfterRead(String key, String value, long currentTime,
//                                                @NonNegative long currentDuration) {
//                        return currentDuration;
//                    }
//                })
                .build();
        return cache;
    }

    AsyncCache<String, User> asyncCache = Caffeine.newBuilder().buildAsync();

    public boolean isDevUser(String userId) {
        // 获取用户信息
        CompletableFuture<User> userFuture = asyncCache.get(userId, s -> userDao.getUser(s));
        // 获取公司研发体系部门列表
        CompletableFuture<List<String>> devDeptFuture =
                CompletableFuture.supplyAsync(() -> departmentDao.getDevDepartments());
        // 等用户信息、研发部门列表都拉取完成后，判断用户是否属于研发体系
        CompletableFuture<Boolean> combineResult =
                userFuture.thenCombine(devDeptFuture,
                        (user, devDepts) -> devDepts.contains(user.getDepartmentId()));
        // 等待执行完成，调用线程获取最终结果
        return combineResult.join();
    }

    public static AsyncLoadingCache<String, String> buildAsyncLoadingCache() {
         return Caffeine.newBuilder()
                .initialCapacity(1000)
                .maximumSize(10000L)
                .expireAfterWrite(30L, TimeUnit.MINUTES)
                .refreshAfterWrite(1L, TimeUnit.MINUTES)
                .removalListener((key, value, cause) ->
                        System.out.println(key + "移除，原因：" + cause))
                .recordStats()
                .buildAsync(key -> key + RandomUtil.randomString(5));
    }

    public static LoadingCache<String, String> buildLoadingCache() {
        return Caffeine.newBuilder()
                .initialCapacity(1000)
                .maximumSize(10000L)
                .expireAfterWrite(30L, TimeUnit.MINUTES)
                .refreshAfterWrite(1L, TimeUnit.MINUTES)
                .removalListener((key, value, cause) ->
                        System.out.println(key + "移除，原因：" + cause))
                .recordStats()
                .build(key -> key + RandomUtil.randomString(5));
    }

//    public static void main(String[] args) throws Exception {
//        AsyncLoadingCache<String, String> asyncLoadingCache = buildAsyncLoadingCache();
//        asyncLoadingCache.put("key1", CompletableFuture.supplyAsync(() -> "value1"));
//        CompletableFuture<String> completableFuture = asyncLoadingCache.get("key1");
//        String value = completableFuture.join();
//        System.out.println(value);
//    }
    public static void main(String[] args) throws Exception {
        LoadingCache<String, String> loadingCache = buildLoadingCache();
        loadingCache.put("key1", "value1");
        String value = loadingCache.get("key1");
        System.out.println(value);
    }
//        try {
////            CaffeineCacheService cacheService = new CaffeineCacheService();
////            Cache<String, User> cache = cacheService.createUserCache();
//
//
//            System.out.println("main thread:" + Thread.currentThread().getId());
//            // 同步方式
//            Cache<String, User> cache = Caffeine.newBuilder().build();
//            cache.get("123", s -> {
//                System.out.println("同步callable thread:" + Thread.currentThread().getId());
//                return userDao.getUser(s);
//            });
////            System.out.println(user);
//            // 异步方式
//            AsyncCache<String, User> asyncCache = Caffeine.newBuilder().buildAsync();
//            asyncCache.get("123", s -> {
//                System.out.println("异步callable thread:" + Thread.currentThread().getId());
//                return userDao.getUser(s);
//            });
//            System.out.println(userCompletableFuture.join());

//            RepositoryId.cache.put("1", "111");
//            RepositoryId.cache.put("2", "222");
//            RepositoryId.cache.put("3", "333");

//            Thread.sleep(1000L);
//
//            System.out.println("读取key2----" + RepositoryId.cache.getIfPresent("2"));
//            Thread.sleep(1000L);

//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        try {
//            CaffeineCacheService cacheService = new CaffeineCacheService();
//            LoadingCache<String, User> userCache = cacheService.createUserCache();
//            System.out.println(userCache.getClass().getCanonicalName());

//            LoadingCache<String, User> cache = Caffeine.newBuilder().build(userId -> userDao.getUser(userId));
//            cache.put("124", new User("124", "张三"));
//            User user = cache.get("123");
//            System.out.println(user);
//            User techUser = cache.get("J234", userId -> {
//                // 仅J开头的用户ID才会去回源
//                if (!StringUtils.isEmpty(userId) && userId.startsWith("J")) {
//                    return userDao.getUser(userId);
//                } else {
//                    return null;
//                }
//            });
//            System.out.println(techUser);

//        User userInfo = cache.getIfPresent("123");
//        System.out.println(userInfo);
//        Map<String, User> presentUsers =
//                cache.getAllPresent(Stream.of("123", "124", "125").collect(Collectors.toList()));
//        System.out.println(presentUsers);
        //        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        try {
//            AsyncCache<String, User> asyncCache = Caffeine.newBuilder().buildAsync();
//            // 获取用户信息
//            CompletableFuture<User> userFuture = asyncCache.get("123", s -> userDao.getUser(s));
//            // 获取公司研发体系部门列表
//            CompletableFuture<List<String>> devDeptFuture =
//                    CompletableFuture.supplyAsync(() -> departmentDao.getDevDepartments());
//            // 等用户信息、研发部门列表都拉取完成后，判断用户是否属于研发体系
//            CompletableFuture<Boolean> combineResult =
//                    userFuture.thenCombine(devDeptFuture,
//                    (user, devDepts) -> devDepts.contains(user.getDepartmentId()));
//            // 等待执行完成，调用线程获取最终结果
//            System.out.println("是否研发体系：" + combineResult.join());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        try {
//            AsyncLoadingCache<String, User> asyncLoadingCache =
//                    Caffeine.newBuilder().buildAsync(key -> userDao.getUser(key));
//            CompletableFuture<User> userCompletableFuture = asyncLoadingCache.get("123");
//            System.out.println(userCompletableFuture.join());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        try {
//
//            Cache<Object, Object> build = Caffeine.newBuilder().build();
//            System.out.println(build.getClass().getCanonicalName());
//            AsyncLoadingCache<String, User> asyncLoadingCache =
//                    Caffeine.newBuilder()
////                            .maximumSize(1000L)
//                            .buildAsync(
//                            (key, executor) -> CompletableFuture.supplyAsync(() -> userDao.getUser(key), executor)
//                    );
//            System.out.println(asyncLoadingCache.getClass().getCanonicalName());
//            CompletableFuture<User> userCompletableFuture = asyncLoadingCache.get("123");
//            System.out.println(userCompletableFuture.join());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        try {
//            LoadingCache<String, User> userCache = Caffeine.newBuilder()
//                    .removalListener((key, value, cause) -> {
//                        System.out.println(key + "移除，原因：" + cause);
//                    })
//                    .expireAfter(new Expiry<String, User>() {
//                        @Override
//                        public long expireAfterCreate(@NonNull String key, @NonNull User value, long currentTime) {
//                            if (key.startsWith("A")) {
//                                return TimeUnit.SECONDS.toNanos(1);
//                            } else {
//                                return TimeUnit.SECONDS.toNanos(2);
//                            }
//                        }
//
//                        @Override
//                        public long expireAfterUpdate(@NonNull String key, @NonNull User value, long currentTime,
//                                                      @NonNegative long currentDuration) {
//                            return Long.MAX_VALUE;
//                        }
//
//                        @Override
//                        public long expireAfterRead(@NonNull String key, @NonNull User value, long currentTime,
//                                                    @NonNegative long currentDuration) {
//                            if (key.startsWith("A")) {
//                                return Long.MAX_VALUE;
//                            } else {
//                                return TimeUnit.MILLISECONDS.toNanos(500);
//                            }
//                        }
//                    })
//                    .build(key -> userDao.getUser(key));
//
//            userCache.put("123", new User("123", "123"));
//            userCache.put("A123", new User("A123", "A123"));
//            userCache.get("123");
//            Thread.sleep(600);
//            System.out.println("---- " + userCache.get("123"));
//            Thread.sleep(100);
//            System.out.println(userCache.get("123"));
//            System.out.println(userCache.get("A123"));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        try {
//            System.out.println("当前主线程：" + Thread.currentThread().getId());
//            LoadingCache<Integer, String> loadingCache = Caffeine.newBuilder()
//                    .maximumSize(1)
//                    .removalListener((key, value, cause) -> {
//                        System.out.println(Thread.currentThread().getId() + "线程， " + key + "淘汰，原因：" + cause);
//                    })
//                    .recordStats()
//                    .build(key -> RandomUtil.randomString(8));
//
//            for (int i = 0; i < 2; i++) {
//                loadingCache.get(i);
//            }
//
////            Thread.sleep(2000L);
//            System.out.println(loadingCache.stats());
//            System.out.println("预估元素个数：" + loadingCache.estimatedSize());
//            Thread.sleep(2000L);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        try {
//            Cache<Integer, String> cache = Caffeine.newBuilder()
//                    .maximumSize(1000L)
//                    .build();
//            cache.put(1, "value1");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        try {
//            Cache<Integer, String> cache = Caffeine.newBuilder()
//                    .maximumWeight(1000L)
//                    .weigher((key, value) -> (String.valueOf(value).length() / 1000) + 1)
//                    .build();
//            cache.put(1, "value1");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//            System.out.println("当前主线程：" + Thread.currentThread().getId());
//            Cache<String, String> cache = Caffeine.newBuilder()
//                    .maximumSize(1)
//                    .removalListener((key, value, cause) ->
//                            System.out.println("数据淘汰执行线程：" + Thread.currentThread().getId()
//                                    + "，" + key + "被移除，原因：" + cause))
//                    .build();
//            cache.put("key1", "value1");
//            System.out.println("key1写入后，当前缓存内的keys：" + cache.asMap().keySet());
//            cache.put("key2", "value1");
//            Thread.sleep(1000L);
//            System.out.println("key2写入后，当前缓存内的keys：" + cache.asMap().keySet());
//            System.out.println("key2写入后，当前缓存内的keys：" + cache.estimatedSize());

//        LoadingCache<String, User> cache = Caffeine.newBuilder()
//                .weakKeys()
//                .weakValues()
//                .softValues()
//                .build(key -> userDao.getUser(key));
//        String key1 = "123";
////        cache.put(key1, "value1");
//        System.out.println(cache.getIfPresent(key1));
//        String key2 = new String("123");
//        System.out.println("key1.equals(key2) ： " + key1.equals(key2));
//        System.out.println("key1==key2 ： " + (key1==key2));
//        System.out.println(cache.getIfPresent(key2));



//        AsyncCache<Object, Object> buildAsync = Caffeine.newBuilder().buildAsync();
//        buildAsync.p

//        Cache<Object, Object> build = Caffeine.newBuilder().build();
//        build.p
//    }
}
