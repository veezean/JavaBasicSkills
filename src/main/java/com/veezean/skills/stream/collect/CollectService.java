package com.veezean.skills.stream.collect;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 演示Stream流收集器Collector使用
 *
 * @author 架构悟道
 * @since 2022/7/16
 */
public class CollectService {

    /**
     * @return 预置的测试数据
     */
    private List<Employee> getAllEmployees() {
        return Stream.of(
                new Employee("上海公司", "研发一部", "大壮", 28, 3000),
                new Employee("上海公司", "研发一部", "二牛", 24, 2000),
                new Employee("上海公司", "研发二部", "铁柱", 34, 5000),
                new Employee("南京公司", "测试一部", "翠花", 27, 3000),
                new Employee("南京公司", "测试二部", "玲玲", 31, 4000)
        ).collect(Collectors.toList());
    }

    /**
     * 过滤指定子公司的员工
     */
    public void filterEmployeesByCompany() {
        List<Employee> employees = getAllEmployees().stream()
                .filter(employee -> "上海公司".equals(employee.getSubCompany()))
                .collect(Collectors.toList());
        System.out.println(employees);
    }

    /**
     * 过滤子公司员工并按照部门分组--常规写法演示
     */
    public void filterEmployeesThenGroup() {
        // 先 筛选
        List<Employee> employees = getAllEmployees().stream()
                .filter(employee -> "上海公司".equals(employee.getSubCompany()))
                .collect(Collectors.toList());

        // 再 分组
        Map<String, List<Employee>> resultMap = new HashMap<>();
        for (Employee employee : employees) {
            List<Employee> groupList = resultMap
                    .computeIfAbsent(employee.getDepartment(), k -> new ArrayList<>());
            groupList.add(employee);
        }

        System.out.println(resultMap);
    }


    /**
     * 过滤子公司员工并按照部门分组--Stream groupingBy写法演示
     */
    public void filterEmployeesThenGroupByStream() {
        Map<String, List<Employee>> resultMap = getAllEmployees().stream()
                .filter(employee -> "上海公司".equals(employee.getSubCompany()))
                .collect(Collectors.groupingBy(Employee::getDepartment));
        System.out.println(resultMap);
    }

    /**
     * summingInt用法演示
     */
    public void calculateSum() {
        Integer salarySum = getAllEmployees().stream()
                .filter(employee -> "上海公司".equals(employee.getSubCompany()))
                .collect(Collectors.summingInt(Employee::getSalary));
        System.out.println(salarySum);
    }

    /**
     * collectingAndThen用法演示
     */
    public void testCollectAndThen() {
        Employee employeeResult = getAllEmployees().stream()
                .filter(employee -> "上海公司".equals(employee.getSubCompany()))
                .collect(
                        Collectors.collectingAndThen(
                                Collectors.maxBy(Comparator.comparingInt(Employee::getSalary)),
                                Optional::get)
                );
        System.out.println(employeeResult);
    }

    /**
     * 演示多个收集器嵌套使用
     * 获取上海子公司员工中工资最高的员工信息
     */
    public void findHighestSalaryEmployee() {
        Optional<Employee> highestSalaryEmployee = getAllEmployees().stream()
                .filter(employee -> "上海公司".equals(employee.getSubCompany()))
                .collect(Collectors.maxBy(Comparator.comparingInt(Employee::getSalary)));
        System.out.println(highestSalaryEmployee.get());
    }

    /**
     * 演示多个收集器嵌套使用 - 简化写法
     * 获取上海子公司员工中工资最高的员工信息
     */
    public void findHighestSalaryEmployee2() {
        Optional<Employee> highestSalaryEmployee = getAllEmployees().stream()
                .filter(employee -> "上海公司".equals(employee.getSubCompany()))
                .max(Comparator.comparingInt(Employee::getSalary));
        System.out.println(highestSalaryEmployee.get());
    }

    /**
     * 演示groupingBy传入一个参数的操作
     */
    public void groupBySubCompany() {
        // 按照子公司维度将员工分组
        Map<String, List<Employee>> resultMap =
                getAllEmployees().stream()
                        .collect(Collectors.groupingBy(Employee::getSubCompany));
        System.out.println(resultMap);
    }

    /**
     * 演示groupingBy传入两个参数的操作
     */
    public void groupAndCaculate() {
        // 按照子公司分组，并统计每个子公司的员工数
        Map<String, Long> resultMap = getAllEmployees().stream()
                .collect(Collectors.groupingBy(Employee::getSubCompany,
                        Collectors.counting()));
        System.out.println(resultMap);
    }

    /**
     * 演示多级分组操作
     */
    public void groupByCompanyAndDepartment() {
        // 按照子公司+部门双层维度，统计各个部门内的人员数
        Map<String, Map<String, Long>> resultMap = getAllEmployees().stream()
                .collect(Collectors.groupingBy(Employee::getSubCompany,
                        Collectors.groupingBy(Employee::getDepartment,
                                Collectors.counting())));
        System.out.println(resultMap);
    }

    /**
     * 演示分区操作
     */
    public void partitionByCompanyAndDepartment() {
        // 统计上海公司和非上海公司的员工总数
        // true表示是上海公司，false表示非上海公司
        Map<Boolean, Long> resultMap = getAllEmployees().stream()
                .collect(Collectors.partitioningBy(e -> "上海公司".equals(e.getSubCompany()),
                        Collectors.counting()));
        System.out.println(resultMap);
    }

    /**
     * 演示自定义收集器的使用
     */
    public void testMyCollector() {
        Integer result = Stream.of(new Score(1), new Score(2), new Score(3), new Score(4))
                .collect(new MyCollector<>(Score::getScore));
        System.out.println(result);
    }

    /**
     * 测试入口
     *
     * @param args
     */
    public static void main(String[] args) {
        CollectService service = new CollectService();
        service.filterEmployeesByCompany();
        service.filterEmployeesThenGroup();
        service.filterEmployeesThenGroupByStream();
        service.calculateSum();
        service.findHighestSalaryEmployee();
        service.findHighestSalaryEmployee2();
        service.groupBySubCompany();
        service.groupAndCaculate();
        service.groupByCompanyAndDepartment();
        service.partitionByCompanyAndDepartment();
        service.testCollectAndThen();
        service.testMyCollector();
    }
}
