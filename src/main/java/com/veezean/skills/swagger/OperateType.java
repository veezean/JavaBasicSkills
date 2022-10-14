package com.veezean.skills.swagger;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <类功能简要描述>
 *
 * @author 架构悟道
 * @since 2022/9/4
 */
@Getter
@AllArgsConstructor
public enum OperateType {
    ADD(1, "新增"),
    MODIFY(2, "更新"),
    DELETE(3, "删除"),
    QUERY(4, "查询");

    private int value;
    private String desc;
}
