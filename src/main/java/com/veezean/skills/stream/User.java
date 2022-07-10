package com.veezean.skills.stream;

/**
 * <类功能简要描述>
 *
 * @author 架构悟道
 * @since 2022/7/10
 */
public class User {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                '}';
    }
}
