package com.veezean.skills.util;

/**
 * 字符串操作工具类
 *
 * @author 架构悟道
 * @since 2022/7/14
 */
public class StringUtils {

    public static boolean isEmpty(String value) {
        return value == null || "".equals(value);
    }
}
