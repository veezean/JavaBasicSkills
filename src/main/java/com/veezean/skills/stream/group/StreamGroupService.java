package com.veezean.skills.stream.group;

import java.util.*;
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

    public void testDownStream() {
        List<Employee> employees = buildEmployeeList();
        Map<String, Long> employeeCountMap = employees.stream().collect(Collectors.groupingBy(Employee::getDepartment,
                Collectors.counting()));
        System.out.println("各部门人数统计：" + employeeCountMap);

        Map<String, Integer> salaryMap = employees.stream().collect(Collectors.groupingBy(Employee::getDepartment,
                Collectors.summingInt(Employee::getSalary)));
        System.out.println("各部门每月工资支出总计：" + salaryMap);

        Map<String, Optional<Employee>> maxSocreEmployeeMap =
                employees.stream().collect(Collectors.groupingBy(Employee::getDepartment,
                        Collectors.maxBy(Comparator.comparing(Employee::getMonthlyScore))));
        System.out.println("各部门月度最佳员工：" + maxSocreEmployeeMap);
    }

    public void testDownStream2() {
        List<Employee> employees = buildEmployeeList();

        Map<String, List<String>> stringListMap = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment,
                        Collectors.mapping(Employee::getName, Collectors.toList())));
        System.out.println(stringListMap);
    }

    private List<Employee> buildEmployeeList() {
        return Stream.of(
                new Employee("张三", "研发部", 80, 3000),
                new Employee("李四", "研发部", 90, 5000),
                new Employee("铁柱", "市场部", 86, 8000),
                new Employee("阿花", "市场部", 79, 6000),
                new Employee("二丫", "产品部", 60, 5000)
        ).collect(Collectors.toList());
    }

    public static void main(String[] args) {
        StreamGroupService service = new StreamGroupService();
        service.groupByDepartment();
        service.groupByDepartmentByStream();
        service.testDownStream();
        service.testDownStream2();
    }
}
