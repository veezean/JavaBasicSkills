package com.veezean.skills.test;

import java.util.Objects;

/**
 * 用户信息定义，根据用户姓名和归属部门可以确定唯一的用户
 */
public class User {
    /**
     * 姓名
     */
    private String name;

    /**
     * 归属部门
     */
    private String department;

    /**
     * 请求敏感资源申请接口的次数
     */
    private int requestCount;

    /**
     * 最新一次申请的资源内容
     */
    private Resource applyedResource;

    public User(String name, String department) {
        this.name = name;
        this.department = department;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return requestCount == user.requestCount &&
                Objects.equals(name, user.name) &&
                Objects.equals(department, user.department);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, department, requestCount);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getRequestCount() {
        return requestCount;
    }

    public void incrementRequestCount() {
        this.requestCount = this.requestCount + 1;
    }

    public Resource getApplyedResource() {
        return applyedResource;
    }

    public void setApplyedResource(Resource applyedResource) {
        this.applyedResource = applyedResource;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", department='" + department + '\'' +
                ", requestCount=" + requestCount +
                ", applyedResource=" + applyedResource +
                '}';
    }
}

