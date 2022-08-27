package com.veezean.skills.function;

/**
 * <类功能简要描述>
 *
 * @author 架构悟道
 * @since 2022/8/10
 */
public class FunctionCodeTest {


    public void caculateSumCost(int count, PriceComputer priceComputer) {
        double computePrice = priceComputer.computePrice(count);
        System.out.println("数量" + count + ", 金额：" + computePrice);
    }

    public static void main(String[] args) {
        FunctionCodeTest functionCodeTest = new FunctionCodeTest();
        int vmCount = 10;
        int diskCount = 20;
        // 计算虚拟机总金额
        functionCodeTest.caculateSumCost(vmCount, count -> 1000 * count);
        // 计算磁盘总金额
        functionCodeTest.caculateSumCost(diskCount, count -> 200 * count);
    }
}
