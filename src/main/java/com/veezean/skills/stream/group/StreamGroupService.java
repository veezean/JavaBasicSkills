package com.veezean.skills.stream.group;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <类功能简要描述>
 *
 * @author 架构悟道
 * @since 2022/7/14
 */
public class StreamGroupService {

    public void groupByDepartment() {
        List<Employee> employees = buildEmployeeList();
        Map<String, List<Employee>> employeeMap = new HashMap<>();
        for (Employee employee : employees) {
            List<Employee> employeeList =
                    employeeMap.computeIfAbsent(employee.getDepartment(), k -> new ArrayList<>());
            employeeList.add(employee);
        }
        System.out.println(employeeMap);
    }

    public void groupByDepartmentByStream() {
        List<Employee> employees = buildEmployeeList();
        Map<String, List<Employee>> employeeMap = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment));
        System.out.println(employeeMap);
    }

    private List<Employee> buildEmployeeList() {
        return Stream.of(
                new Employee("张三", "研发部"),
                new Employee("李四", "研发部"),
                new Employee("铁柱", "市场部"),
                new Employee("阿花", "市场部"),
                new Employee("二丫", "产品部")
        ).collect(Collectors.toList());
    }

    public static void main(String[] args) {
        StreamGroupService service = new StreamGroupService();
        service.groupByDepartment();
        service.groupByDepartmentByStream();
    }
}
