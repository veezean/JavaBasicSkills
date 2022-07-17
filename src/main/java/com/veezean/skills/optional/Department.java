package com.veezean.skills.optional;

import lombok.Data;

/**
 * 部门信息
 *
 * @author 架构悟道
 * @since 2022/7/13
 */
@Data
public class Department {
    private String departmentName;
    private Company company;
}
