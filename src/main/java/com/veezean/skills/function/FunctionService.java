package com.veezean.skills.function;

import lombok.extern.slf4j.Slf4j;

/**
 * <类功能简要描述>
 *
 * @author 架构悟道
 * @since 2022/7/26
 */
public class FunctionService {

    public void testNonLambdaUsage() {
        new Thread() {
            @Override
            public void run() {
                System.out.println("new thread executing...");
            }
        }.start();
    }

    public void testLambdaUsage() {
        new Thread(() -> System.out.println("new thread executing...")).start();
    }


    public void test1() {

    }
}
