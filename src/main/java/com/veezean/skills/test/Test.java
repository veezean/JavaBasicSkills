package com.veezean.skills.test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 辅助测试调用类
 */
public class Test {

    private static ExecutorService threadPool = Executors.newFixedThreadPool(100);

    public static void main(String[] args) {

        SensitiveResourceApplyService applyService = new SensitiveResourceApplyService(loadWhiteListUsers());

        List<Future<?>> futures = new ArrayList<>();

        // 模拟不同的人的多次申请操作
        int callTimes = 20;
        while (callTimes-- > 0) {
            Future<?> future = threadPool.submit(() -> applyService.applyResource(new User("小张", "研发部")));
            futures.add(future);
        }

        callTimes = 40;
        while (callTimes-- > 0) {
            Future<?> future = threadPool.submit(() -> applyService.applyResource(new User("小李", "人事部")));
            futures.add(future);
        }

        callTimes = 660;
        while (callTimes-- > 0) {
            Future<?> future = threadPool.submit(() -> applyService.applyResource(new User("老胡", "人事部")));
            futures.add(future);
        }

        callTimes = 140;
        while (callTimes-- > 0) {
            Future<?> future = threadPool.submit(() -> applyService.applyResource(new User("老刘", "研发部")));
            futures.add(future);
        }

        callTimes = 160;
        while (callTimes-- > 0) {
            Future<?> future = threadPool.submit(() -> applyService.applyResource(new User("小张", "市场部")));
            futures.add(future);
        }

        // 等待处理完毕
        futures.forEach(future -> {
            try {
                future.get();
            } catch (Exception e) {
            }
        });

        // 获取结果测试
        List<User> frequentApplyUsers = applyService.getFrequentApplyUsers();
        int totalRequestCount = applyService.getTotalRequestCount();

        System.out.println("频繁访问申请接口的用户列表：");
        for (User user : frequentApplyUsers) {
            System.out.println(user);
        }
        System.out.println("总请求数：" + totalRequestCount);
    }


    private static HashSet<User> loadWhiteListUsers() {
        HashSet<User> users = new HashSet<>();
        users.add(new User("小张", "研发部"));
        users.add(new User("小李", "人事部"));
        users.add(new User("老王", "研发部"));
        users.add(new User("老刘", "研发部"));
        users.add(new User("小张", "市场部"));
        return users;
    }

}
