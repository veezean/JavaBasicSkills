package com.veezean.skills.future;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * <类功能简要描述>
 *
 * @author 架构悟道
 * @since 2022/7/22
 */
public class LogHelper {

    public static void printLog(String logContent) {
        System.out.println(getCurrentTime() + currentThreadId()  + logContent);
    }

    private static String getCurrentTime() {
        LocalTime time = LocalTime.now();
        return time.format(DateTimeFormatter.ofPattern("[HH:mm:ss.SSS]"));
    }

    private static String currentThreadId() {
        return "[" + Thread.currentThread().getName() + "|" + Thread.currentThread().getId() + "]";
    }
}
