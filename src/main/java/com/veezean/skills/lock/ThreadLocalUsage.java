package com.veezean.skills.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2022/7/28
 */
public class ThreadLocalUsage {

    private static final ThreadLocal<String> TOKEN = ThreadLocal.withInitial(() -> "");
    private volatile ExecutorService threadPool = Executors.newFixedThreadPool(3);

    public void testReleaseThreadLocalSafely() {
        threadPool.submit(this::mockServiceOperations);
    }

    private void mockServiceOperations() {

    }
}
