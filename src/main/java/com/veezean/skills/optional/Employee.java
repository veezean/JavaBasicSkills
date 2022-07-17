package com.veezean.skills.optional;

import lombok.Data;

/**
 * 员工信息
 *
 * @author 架构悟道
 * @since 2022/7/13
 */
@Data
public class Employee {
    private String employeeName;
    private Team team;
}
