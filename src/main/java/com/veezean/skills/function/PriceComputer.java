package com.veezean.skills.function;

import java.util.List;

/**
 * <类功能简要描述>
 *
 * @author wangweiren
 * @since 2022/8/11
 */
@FunctionalInterface
public interface PriceComputer<T> {
    double computePrice(List<T> objects);
}
