package com.veezean.skills.function;

import lombok.Data;

/**
 * <类功能简要描述>
 *
 * @author 架构悟道
 * @since 2022/8/11
 */
@Data
public class VmDetail {
    private int cpuCores;
    private int memSizeG;
    private int diskSizeG;
    private String diskType;
}
