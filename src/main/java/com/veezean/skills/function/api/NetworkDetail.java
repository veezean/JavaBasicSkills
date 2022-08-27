package com.veezean.skills.function.api;

import lombok.Data;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2022/8/11
 */
@Data
public class NetworkDetail implements IResource{
    private int bandWidthM;

    @Override
    public double calculatePrice() {
        return 0;
    }
}
