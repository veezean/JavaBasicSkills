package com.veezean.skills.function.api;

import lombok.Data;

/**
 * <类功能简要描述>
 *
 * @author 架构悟道
 * @since 2022/8/11
 */
@Data
public class VmDetail implements IResource{
    private int cpuCores;
    private int memSizeG;
    private int diskSizeG;
    private String diskType;

    @Override
    public double calculatePrice() {
        return 0;
    }
}
