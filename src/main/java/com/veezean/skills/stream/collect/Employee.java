package com.veezean.skills.stream.collect;

import lombok.Data;

/**
 * 员工信息类
 *
 * @author 架构悟道
 * @since 2022/7/16
 */
@Data
public class Employee {
    private String subCompany;
    private String department;
    private String name;
    private int age;
    private int salary;

    public Employee(String subCompany, String department, String name, int age, int salary) {
        this.subCompany = subCompany;
        this.department = department;
        this.name = name;
        this.age = age;
        this.salary = salary;
    }
}
