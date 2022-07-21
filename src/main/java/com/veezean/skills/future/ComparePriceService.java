package com.veezean.skills.future;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Stream;

/**
 * <类功能简要描述>
 *
 * @author 架构悟道
 * @since 2022/7/20
 */
public class ComparePriceService {

    /**
     * 【串行】获取多个平台比价信息得到最低价格平台
     *
     * @param product
     * @return
     */
    public PriceResult getCheapestPlatAndPrice(String product) {
        // 获取某宝的价格以及优惠
        PriceResult mouBaoPrice = HttpRequestMock.getMouBaoPrice(product);
        int mouBaoDiscounts = HttpRequestMock.getMouBaoDiscounts(product);
        mouBaoPrice.setRealPrice(mouBaoPrice.getPrice() - mouBaoDiscounts);
        System.out.println(mouBaoPrice);

        // 获取某东的价格以及优惠
        PriceResult mouDongPrice = HttpRequestMock.getMouDongPrice(product);
        int mouDongDiscounts = HttpRequestMock.getMouDongDiscounts(product);
        mouDongPrice.setRealPrice(mouDongPrice.getPrice() - mouDongDiscounts);
        System.out.println(mouDongPrice);

        // 获取某夕夕的价格以及优惠
        PriceResult mouXiXiPrice = HttpRequestMock.getMouXiXiPrice(product);
        int mouXiXiDiscounts = HttpRequestMock.getMouXiXiDiscounts(product);
        mouXiXiPrice.setRealPrice(mouXiXiPrice.getPrice() - mouXiXiDiscounts);
        System.out.println(mouXiXiPrice);

        // 计算并选出实际价格最低的平台
        return Stream.of(mouBaoPrice, mouDongPrice, mouXiXiPrice).
                min(Comparator.comparingInt(PriceResult::getRealPrice))
                .get();
    }

    /**
     * 演示传统方式通过线程池来增加并发
     *
     * @param product
     * @return
     */
    public PriceResult getCheapestPlatAndPrice2(String product) {
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        List<Future<PriceResult>> futureList = new ArrayList<>();

        Future<PriceResult> mouBaoFuture = executorService.submit(() -> {
            PriceResult mouBaoPrice = HttpRequestMock.getMouBaoPrice(product);
            int mouBaoDiscounts = HttpRequestMock.getMouBaoDiscounts(product);
            mouBaoPrice.setRealPrice(mouBaoPrice.getPrice() - mouBaoDiscounts);
            System.out.println(mouBaoPrice);
            return mouBaoPrice;
        });
        futureList.add(mouBaoFuture);

        Future<PriceResult> mouDongFuture = executorService.submit(() -> {
            PriceResult mouDongPrice = HttpRequestMock.getMouDongPrice(product);
            int mouDongDiscounts = HttpRequestMock.getMouDongDiscounts(product);
            mouDongPrice.setRealPrice(mouDongPrice.getPrice() - mouDongDiscounts);
            System.out.println(mouDongPrice);
            return mouDongPrice;
        });
        futureList.add(mouDongFuture);

        Future<PriceResult> mouXiXiFuture = executorService.submit(() -> {
            PriceResult mouXiXiPrice = HttpRequestMock.getMouXiXiPrice(product);
            int mouXiXiDiscounts = HttpRequestMock.getMouXiXiDiscounts(product);
            mouXiXiPrice.setRealPrice(mouXiXiPrice.getPrice() - mouXiXiDiscounts);
            System.out.println(mouXiXiPrice);
            return mouXiXiPrice;
        });
        futureList.add(mouXiXiFuture);

        List<PriceResult> results = new ArrayList<>();
        try {
            for (Future<PriceResult> future : futureList) {
                results.add(future.get());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return results.stream()
                .min(Comparator.comparingInt(PriceResult::getRealPrice))
                .get();

    }

    public PriceResult getCheapestPlatAndPrice3(String product) {
        CompletableFuture<PriceResult> mouBao =
                CompletableFuture.supplyAsync(() -> HttpRequestMock.getMouXiXiPrice(product))
                        .thenCombine(CompletableFuture.supplyAsync(() -> HttpRequestMock.getMouXiXiDiscounts(product)),
                                this::computeRealPrice);

        CompletableFuture<PriceResult> mouDong =
                CompletableFuture.supplyAsync(() -> HttpRequestMock.getMouXiXiPrice(product))
                        .thenCombine(CompletableFuture.supplyAsync(() -> HttpRequestMock.getMouXiXiDiscounts(product)),
                                this::computeRealPrice);

        CompletableFuture<PriceResult> mouXiXi =
                CompletableFuture.supplyAsync(() -> HttpRequestMock.getMouXiXiPrice(product))
                        .thenCombine(CompletableFuture.supplyAsync(() -> HttpRequestMock.getMouXiXiDiscounts(product)),
                                this::computeRealPrice);

        return Stream.of(mouBao, mouDong, mouXiXi)
                .map(CompletableFuture::join)
                .sorted(Comparator.comparingInt(PriceResult::getRealPrice))
                .findFirst()
                .get();
    }

    private PriceResult computeRealPrice(PriceResult priceResult, int disCounts) {
        priceResult.setRealPrice(priceResult.getPrice() - disCounts);
        return priceResult;
    }

    public static void main(String[] args) {
        ComparePriceService service = new ComparePriceService();
        long startTime = System.currentTimeMillis();
        PriceResult result = service.getCheapestPlatAndPrice("Huawei P50");
        System.out.println("获取最优价格信息：" + result);
        System.out.println("-----执行耗时： " + (System.currentTimeMillis() - startTime) + "ms  ------");
    }
}
