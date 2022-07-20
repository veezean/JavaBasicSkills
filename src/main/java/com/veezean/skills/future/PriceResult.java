package com.veezean.skills.future;

import lombok.Data;

/**
 * <类功能简要描述>
 *
 * @author 架构悟道
 * @since 2022/7/20
 */
@Data
public class PriceResult {
    private int price;
    private int discounts;
    private int realPrice;
    private String platform;

    public PriceResult(String platform) {
        this.platform = platform;
    }
}
