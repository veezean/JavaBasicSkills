package com.veezean.skills.optional;

import com.veezean.skills.util.StringUtils;

import javax.swing.text.html.Option;
import java.util.Optional;

/**
 * Optional演示类
 *
 * @author 架构悟道
 * @since 2022/7/12
 */
public class OptionalService {

    /**
     * 演示创建Optional的方法
     */
    public void testCreateOptional() {
        // 使用Optional.of构造出具体对象的封装Optional对象
        System.out.println(Optional.of(new Content("111", "JiaGouWuDao")));
        // 使用Optional.empty构造一个不代表任何对象的空Optional值
        System.out.println(Optional.empty());
        System.out.println(Optional.ofNullable(null));
        System.out.println(Optional.ofNullable(new Content("222", "JiaGouWuDao22")));
    }

    /**
     * 演示Optional.of方法如果传null会抛异常
     */
    public void testCreateOptional2() {
        // 使用Optional.of方法时，需要确保入参不为null，否则会空指针
        System.out.println("-----下面会有空指针----");
        try {
            System.out.println(Optional.of(null));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("------上面会有空指针------");
    }

    /**
     * 演示Optional的错误用法
     */
    public void testCallOptional() {
        Optional<Content> optional = getContent();
        System.out.println("-------下面代码会报异常--------");
        try {
            // 【错误用法】直接从Optional对象中get()实际参数，这种效果与返回null对象然后直接调用是一样的效果
            Content content = optional.get();
            System.out.println(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("-------上面代码会报异常--------");
    }

    /**
     * 演示Optional的不太优雅的用法
     */
    public void testCallOptional2() {
        Optional<Content> optional = getContent();
        // 使用前先判断下元素是否存在
        if (optional.isPresent()) {
            Content content = optional.get();
            System.out.println(content);
        }
    }

    private Optional<Content> getContent() {
        return Optional.ofNullable(null);
    }

    public void testNullReturn() {
        Content content = getContent2();
        System.out.println("-------下面代码会报异常--------");
        try {
            // 【错误用法】调用前没有判空
            String contentValue = content.getValue();
            System.out.println(contentValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("-------上面代码会报异常--------");
    }

    public void testNullReturn2() {
        Content content = getContent2();
        if (content != null) {
            System.out.println(content.getValue());
        }
    }

    private Content getContent2() {
        return null;
    }

    /**
     * 演示多级调用的时候可能的空指针问题
     */
    public void getCompanyFromEmployee() {
        Employee employee = getEmployee();
        Company company = employee.getTeam().getDepartment().getCompany();
        System.out.println(company);
    }

    /**
     * 演示多级调用的时候空指针异常保护
     */
    public void getCompanyFromEmployee2() {
        Employee employee = getEmployee();
        if (employee == null) {
            // do something here...
            return;
        }
        Team team = employee.getTeam();
        if (team == null) {
            // do something here...
            return;
        }

        Department department = team.getDepartment();
        if (department == null) {
            // do something here...
            return;
        }
        Company company = department.getCompany();
        System.out.println(company);
    }

    private Employee getEmployee() {
        Employee employee = new Employee();
        employee.setEmployeeName("JiaGouWuDao");
        employee.setTeam(new Team("DevTeam4"));
        return employee;
    }

    public void testMapAndFlatMap() {
        Optional<User> userOptional = getUser();
        Optional<Employee> employeeOptional = userOptional.map(user -> {
            Employee employee = new Employee();
            employee.setEmployeeName(user.getUserName());
            // map与flatMap的区别点：此处return的是具体对象类型
            return employee;
        });
        System.out.println(employeeOptional);

        Optional<Employee> employeeOptional2 = userOptional.flatMap(user -> {
            Employee employee = new Employee();
            employee.setEmployeeName(user.getUserName());
            // map与flatMap的区别点：此处return的是具体对象的Optional封装类型
            return Optional.of(employee);
        });
        System.out.println(employeeOptional2);
    }

    private Optional<User> getUser() {
        User user = new User();
        user.setId("111");
        user.setUserName("JiaGouWuDao");
        return Optional.of(user);
    }

    public void getCompanyFromEmployeeTest() {
        Employee employeeDetail = getEmployee();
        String companyName = Optional.ofNullable(employeeDetail)
                .map(employee -> employee.getTeam())
                .map(team -> team.getDepartment())
                .map(department -> department.getCompany())
                .map(company -> company.getCompanyName())
                .orElse("No Company");
        System.out.println(companyName);
    }

    public String getClientIp(HttpServletRequest request) {
        String clientIp = request.getHeader("X-Forwarded-For");
        if (!StringUtils.isEmpty(clientIp)) {
            return clientIp;
        }
        clientIp = request.getHeader("X-Real-IP");
        return clientIp;
    }

    public String getClientIp2(HttpServletRequest request) {
        String clientIp = request.getHeader("X-Forwarded-For");
        return Optional.ofNullable(clientIp).orElseGet(() -> request.getHeader("X-Real-IP"));
    }

//    public Optional<FileInfo> queryOssFileInfo(String fileId) {
//        FileEntity entity = fileRepository.findByIdAndStatus(fileId, 0);
//        if (entity != null) {
//            return Optional.ofNullable(new FileInfo(entity.getName(), entity.getFilePath(), false));
//        }
//        FileHistoryEntity hisEntity = fileHisRepository.findByIdAndStatus(fileId, 0);
//        if (hisEntity != null) {
//            return Optional.ofNullable(new FileInfo(hisEntity.getName(), hisEntity.getFilePath(), true));
//        }
//        return Optional.empty();
//    }


    public Team getTeamInfo() throws TestException {
        Employee employee = getEmployee();
        Team team = employee.getTeam();
        if (team == null) {
            throw new TestException("team is missing");
        }
        return team;
    }

    public static void main(String[] args) {
        OptionalService service = new OptionalService();
        service.testCreateOptional();
        service.testCreateOptional2();
        service.testCallOptional();
        service.testCallOptional2();
        service.testNullReturn();
        service.testNullReturn2();
        service.testMapAndFlatMap();
        service.getCompanyFromEmployeeTest();
    }
}
