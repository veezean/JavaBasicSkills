package com.veezean.skills.future;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <类功能简要描述>
 *
 * @author 架构悟道
 * @since 2022/7/20
 */
public class ComparePriceService {

    private  ExecutorService threadPool = Executors.newFixedThreadPool(5);

    /**
     * 【串行】获取多个平台比价信息得到最低价格平台
     *
     * @param product
     * @return
     */
    public PriceResult getCheapestPlatAndPrice(String product) {
        // 获取某宝的价格以及优惠
        PriceResult mouBaoPrice = computeRealPrice(HttpRequestMock.getMouBaoPrice(product),
                HttpRequestMock.getMouBaoDiscounts(product));
        // 获取某东的价格以及优惠
        PriceResult mouDongPrice = computeRealPrice(HttpRequestMock.getMouDongPrice(product),
                HttpRequestMock.getMouDongDiscounts(product));
        // 获取某夕夕的价格以及优惠
        PriceResult mouXiXiPrice = computeRealPrice(HttpRequestMock.getMouXiXiPrice(product),
                HttpRequestMock.getMouXiXiDiscounts(product));

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
        Future<PriceResult> mouBaoFuture =
                threadPool.submit(() -> computeRealPrice(HttpRequestMock.getMouBaoPrice(product),
                        HttpRequestMock.getMouBaoDiscounts(product)));
        Future<PriceResult> mouDongFuture =
                threadPool.submit(() -> computeRealPrice(HttpRequestMock.getMouDongPrice(product),
                        HttpRequestMock.getMouDongDiscounts(product)));
        Future<PriceResult> mouXiXiFuture =
                threadPool.submit(() -> computeRealPrice(HttpRequestMock.getMouXiXiPrice(product),
                        HttpRequestMock.getMouXiXiDiscounts(product)));

        // 等待所有线程结果都处理完成，然后从结果中计算出最低价
        return Stream.of(mouBaoFuture, mouDongFuture, mouXiXiFuture)
                .map(priceResultFuture -> {
                    try {
                        return priceResultFuture.get(5L, TimeUnit.SECONDS);
                    } catch (Exception e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .min(Comparator.comparingInt(PriceResult::getRealPrice))
                .get();
    }

    /**
     * 演示并行处理的模式
     *
     * @param product
     * @return
     */
    public PriceResult getCheapestPlatAndPrice3(String product) {
        // 获取并计算某宝的最终价格
        CompletableFuture<PriceResult> mouBao =
                CompletableFuture.supplyAsync(() -> HttpRequestMock.getMouBaoPrice(product))
                        .thenCombine(CompletableFuture.supplyAsync(() -> HttpRequestMock.getMouBaoDiscounts(product)),
                                this::computeRealPrice);
        // 获取并计算某宝的最终价格
        CompletableFuture<PriceResult> mouDong =
                CompletableFuture.supplyAsync(() -> HttpRequestMock.getMouDongPrice(product))
                        .thenCombine(CompletableFuture.supplyAsync(() -> HttpRequestMock.getMouDongDiscounts(product)),
                                this::computeRealPrice);
        // 获取并计算某宝的最终价格
        CompletableFuture<PriceResult> mouXiXi =
                CompletableFuture.supplyAsync(() -> HttpRequestMock.getMouXiXiPrice(product))
                        .thenCombine(CompletableFuture.supplyAsync(() -> HttpRequestMock.getMouXiXiDiscounts(product)),
                                this::computeRealPrice);

        // 排序并获取最低价格
        return Stream.of(mouBao, mouDong, mouXiXi)
                .map(CompletableFuture::join)
                .sorted(Comparator.comparingInt(PriceResult::getRealPrice))
                .findFirst()
                .get();
    }


    /**
     * 演示thenCombine与thenCombineAsync区别
     *
     * @param product
     * @return
     */
    public PriceResult getCheapestPlatAndPrice4(String product) {
        // 构造自定义线程池
        ExecutorService executor = Executors.newFixedThreadPool(5);

        return
                CompletableFuture.supplyAsync(
                        () -> HttpRequestMock.getMouXiXiPrice(product),
                        executor
                ).thenCombineAsync(
                        CompletableFuture.supplyAsync(() -> HttpRequestMock.getMouXiXiDiscounts(product)),
                        this::computeRealPrice,
                        executor
                ).join();
    }

    /**
     * 演示thenCombine与thenCombineAsync区别
     *
     * @param product
     * @return
     */
    public PriceResult getCheapestPlatAndPrice5(String product) {
        return
                CompletableFuture.supplyAsync(
                        () -> HttpRequestMock.getMouXiXiPrice(product)
                ).thenCombine(
                        CompletableFuture.supplyAsync(() -> HttpRequestMock.getMouXiXiDiscounts(product)),
                        this::computeRealPrice
                ).join();
    }

    public void testGetAndJoin(String product) {
        // join无需显式try...catch...
        PriceResult joinResult = CompletableFuture.supplyAsync(() -> HttpRequestMock.getMouXiXiPrice(product))
                .join();

        try {
            // get显式try...catch...
            PriceResult getResult = CompletableFuture.supplyAsync(() -> HttpRequestMock.getMouXiXiPrice(product))
                    .get(5L, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 演示结合Stream的时候，到底是一个Stream中操作，还是2个Stream中操作，区别
     *
     * @param products
     * @return
     */
    public PriceResult comparePriceInOnePlat(List<String> products) {
        return products.stream()
                .map(product ->
                        CompletableFuture.supplyAsync(() -> HttpRequestMock.getMouBaoPrice(product))
                                .thenCombine(
                                        CompletableFuture.supplyAsync(() -> HttpRequestMock.getMouBaoDiscounts(product)),
                                        this::computeRealPrice))
                .map(CompletableFuture::join)
                .sorted(Comparator.comparingInt(PriceResult::getRealPrice))
                .findFirst()
                .get();
    }

    /**
     * 演示两个map写法等同于一个map写法
     *
     * @param products
     * @return
     */
    public PriceResult comparePriceInOnePlat1(List<String> products) {
        return products.stream()
                .map(product ->
                        CompletableFuture.supplyAsync(() -> HttpRequestMock.getMouBaoPrice(product))
                                .thenCombine(
                                        CompletableFuture.supplyAsync(() -> HttpRequestMock.getMouBaoDiscounts(product)),
                                        this::computeRealPrice)
                                .join())
                .sorted(Comparator.comparingInt(PriceResult::getRealPrice))
                .findFirst()
                .get();
    }

    public void testCreateFuture(String product) {
        // supplyAsync， 执行逻辑有返回值PriceResult
        CompletableFuture<PriceResult> supplyAsyncResult =
                CompletableFuture.supplyAsync(() -> HttpRequestMock.getMouBaoPrice(product));
        // runAsync, 执行逻辑没有返回值
        CompletableFuture<Void> runAsyncResult =
                CompletableFuture.runAsync(() -> System.out.println(product));
    }

    public void testStepByStep(CompletableFuture<PriceResult> supplyAsyncResult) {

        CompletableFuture<Integer> applyResult =
                supplyAsyncResult.thenApply(PriceResult::getRealPrice);
        CompletableFuture<Integer> composeResult =
                supplyAsyncResult.thenCompose(priceResult -> CompletableFuture.supplyAsync(priceResult::getPrice));
        CompletableFuture<Void> voidCompletableFuture =
                supplyAsyncResult.thenAccept(priceResult -> System.out.println(priceResult.getPrice()));
        supplyAsyncResult.thenRun(() -> {
        });

    }

    /**
     * Stream分开，并行模式
     *
     * @param products
     * @return
     */
    public PriceResult comparePriceInOnePlat2(List<String> products) {
        List<CompletableFuture<PriceResult>> completableFutures = products.stream()
                .map(product ->
                        CompletableFuture.supplyAsync(() -> HttpRequestMock.getMouBaoPrice(product))
                                .thenCombine(
                                        CompletableFuture.supplyAsync(() -> HttpRequestMock.getMouBaoDiscounts(product)),
                                        this::computeRealPrice))
                .collect(Collectors.toList());

        return completableFutures.stream()
                .map(CompletableFuture::join)
                .sorted(Comparator.comparingInt(PriceResult::getRealPrice))
                .findFirst()
                .get();
    }


    private PriceResult computeRealPrice(PriceResult priceResult, int disCounts) {
        priceResult.setRealPrice(priceResult.getPrice() - disCounts);
        LogHelper.printLog(priceResult.getPlatform() + "最终价格计算完成：" + priceResult.getRealPrice());
        return priceResult;
    }

//    public PriceResult testThenApply(String product) {
//        // 获取并计算某宝的最终价格
//        CompletableFuture<PriceResult> mouBao =
//                CompletableFuture.supplyAsync(() -> HttpRequestMock.getMouBaoPrice(product))
//                        .thenCombine(CompletableFuture.supplyAsync(() -> HttpRequestMock.getMouBaoDiscounts(product)),
//                                this::computeRealPrice);
//        // 获取并计算某宝的最终价格
//        CompletableFuture<PriceResult> mouDong =
//                CompletableFuture.supplyAsync(() -> HttpRequestMock.getMouDongPrice(product))
//                        .thenCombine(CompletableFuture.supplyAsync(() -> HttpRequestMock.getMouDongDiscounts
//                        (product)),
//                                this::computeRealPrice);
//
//
//    }

    public void testExceptionHandle() {
        CompletableFuture.supplyAsync(() -> {
            throw new RuntimeException("supplyAsync excetion occurred...");
        }).handle((obj, e) -> {
            if (e != null) {
                System.out.println("thenApply executed, exception occurred...");
            }
            return obj;
        }).join();
    }

    public void testCombineHandle() {
        System.out.println("开始执行");
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> getResult("future1"));
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> "future2");
        CompletableFuture.allOf(future1, future2).join();
        System.out.println("执行完成");
    }

    private String getResult(String result) {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
        ComparePriceService service = new ComparePriceService();
        long startTime = System.currentTimeMillis();
//        PriceResult result = service.getCheapestPlatAndPrice5("Iphone13");
//        System.out.println("获取最优价格信息：" + result);
        service.testCombineHandle();

//        long startTime = System.currentTimeMillis();
//        PriceResult result = service.comparePriceInOnePlat2(Arrays.asList("Iphone13黑色", "Iphone13白色", "Iphone13红色"));
//        System.out.println("获取最优价格信息：" + result);

        System.out.println("-----执行耗时： " + (System.currentTimeMillis() - startTime) + "ms  ------");
    }
}
