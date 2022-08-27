package com.veezean.skills.function;

import lombok.Data;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2022/8/11
 */
@Data
public class PriceInfo {
    private double price;
    private double tax;
    private double taxRate;
    private double totalPay;
}
