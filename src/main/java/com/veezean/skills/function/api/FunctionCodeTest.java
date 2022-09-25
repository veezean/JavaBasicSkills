package com.veezean.skills.function.api;

import com.veezean.skills.function.NetworkDetail;
import com.veezean.skills.function.PriceInfo;
import com.veezean.skills.function.VmDetail;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <类功能简要描述>
 *
 * @author 架构悟道
 * @since 2022/8/11
 */
public class FunctionCodeTest {

    public PriceInfo  calculatePriceInfo(List<IResource> resources) {
        // 计算总价
        double price = resources.stream().collect(Collectors.summarizingDouble(IResource::calculatePrice)).getSum();

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

//        // 计算虚拟机总金额
//        functionCodeTest.calculatePriceInfo(vmDetailList);
//
//        // 计算磁盘总金额
//        functionCodeTest.calculatePriceInfo(networkDetailList);
    }

}
