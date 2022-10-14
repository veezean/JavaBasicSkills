package com.veezean.skills.classloader;

import sun.misc.Launcher;

import java.util.Arrays;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2022/9/8
 */
public class Main {

    public static void main(String[] args) {
        String var1 = System.getProperty("java.class.path");
        System.out.println(var1);
        String var0 = System.getProperty("java.ext.dirs");
        System.out.println(var0);
        System.out.println(Arrays.toString(Launcher.getBootstrapClassPath().getURLs()));
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        System.out.println(contextClassLoader);
        System.out.println(contextClassLoader.getParent());
        System.out.println(contextClassLoader.getParent().getParent());
    }
}
