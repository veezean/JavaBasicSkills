package com.veezean.skills.future;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * 模拟对外请求的具体耗时服务
 *
 * @author 架构悟道
 * @since 2022/7/20
 */
public class HttpRequestMock {

    public static PriceResult getMouBaoPrice(String product) {
        LogHelper.printLog("获取某宝上 " + product + "的价格");
        mockCostTimeOperationn();

        PriceResult result = new PriceResult("某宝");
        result.setPrice(5199);
        LogHelper.printLog("获取某宝上 " + product + "的价格完成： 5199");
        return result;
    }

    public static int getMouBaoDiscounts(String product) {
        LogHelper.printLog("获取某宝上 " + product + "的优惠");
        mockCostTimeOperationn();
        LogHelper.printLog("获取某宝上 " + product + "的优惠完成： -200");
        return 200;
    }

    public static PriceResult getMouDongPrice(String product) {
        LogHelper.printLog("获取某东上 " + product + "的价格");
        mockCostTimeOperationn();

        PriceResult result = new PriceResult("某东");
        result.setPrice(5299);
        LogHelper.printLog("获取某东上 " + product + "的价格完成： 5299");
        return result;
    }

    public static int getMouDongDiscounts(String product) {
        LogHelper.printLog("获取某东上 " + product + "的优惠");
        mockCostTimeOperationn();
        LogHelper.printLog("获取某东上 " + product + "的优惠完成： -150");
        return 150;
    }

    public static PriceResult getMouXiXiPrice(String product) {
        LogHelper.printLog("获取某夕夕上 " + product + "的价格");
        mockCostTimeOperationn();

        PriceResult result = new PriceResult("某夕夕");
        result.setPrice(5399);
        LogHelper.printLog("获取某夕夕上 " + product + "的价格完成： 5399");
        return result;
    }

    public static int getMouXiXiDiscounts(String product) {
        LogHelper.printLog("获取某夕夕上 " + product + "的优惠");
        mockCostTimeOperationn();
        LogHelper.printLog("获取某夕夕上 " + product + "的优惠完成： -5300");
        return 5300;
    }

    private static void mockCostTimeOperationn() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
