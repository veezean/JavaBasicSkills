package com.veezean.skills.future;

import java.util.Random;

/**
 * 模拟对外请求的具体耗时服务
 *
 * @author 架构悟道
 * @since 2022/7/20
 */
public class HttpRequestMock {

    public static PriceResult getMouBaoPrice(String product) {
        mockCostTimeOperationn();

        PriceResult result = new PriceResult("MouBao");
        result.setPrice(10);
        return result;
    }

    public static int getMouBaoDiscounts(String product) {
        mockCostTimeOperationn();
        return 3;
    }

    public static PriceResult getMouDongPrice(String product) {
        mockCostTimeOperationn();

        PriceResult result = new PriceResult("MouDong");
        result.setPrice(14);
        return result;
    }

    public static int getMouDongDiscounts(String product) {
        mockCostTimeOperationn();
        return 8;
    }

    public static PriceResult getMouXiXiPrice(String product) {
        mockCostTimeOperationn();

        PriceResult result = new PriceResult("MouXiXi");
        result.setPrice(9);
        return result;
    }

    public static int getMouXiXiDiscounts(String product) {
        mockCostTimeOperationn();
        return 8;
    }

    private static void mockCostTimeOperationn() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
