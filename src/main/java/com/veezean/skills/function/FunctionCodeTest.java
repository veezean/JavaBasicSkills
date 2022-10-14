package com.veezean.skills.function;

import java.util.ArrayList;
import java.util.List;

/**
 * <类功能简要描述>
 *
 * @author 架构悟道
 * @since 2022/8/11
 */
public class FunctionCodeTest {

    public <T> PriceInfo  calculatePriceInfo(List<T> resources, PriceComputer<T> priceComputer) {
        // 调用函数式接口获取计算结果
        double price = priceComputer.computePrice(resources);

        // 执行后续处理策略
        PriceInfo priceInfo = new PriceInfo();
        priceInfo.setPrice(price);
        priceInfo.setTaxRate(0.15);
        priceInfo.setTax(price * 0.15);
        priceInfo.setTotalPay(priceInfo.getPrice() + priceInfo.getTax());
        return priceInfo;
    }

    public static void main(String[] args) {
        FunctionCodeTest functionCodeTest = new FunctionCodeTest();
        int vmCount = 10;
        int diskCount = 20;
        List<VmDetail> vmDetailList = new ArrayList<>();
        List<NetworkDetail> networkDetailList = new ArrayList<>();

        // 计算虚拟机总金额
        functionCodeTest.calculatePriceInfo(vmDetailList, objects -> {
            double result = 0d;
            for (VmDetail vmDetail : objects) {
                result += 100 * vmDetail.getCpuCores() + 10 * vmDetail.getDiskSizeG() + 50 * vmDetail.getMemSizeG();
            }
            return result;
        });

        // 计算磁盘总金额
        functionCodeTest.calculatePriceInfo(networkDetailList, objects -> {
            double result = 0d;
            for (NetworkDetail networkDetail : objects) {
                result += 20 * networkDetail.getBandWidthM();
            }
            return result;
        });
    }
}
