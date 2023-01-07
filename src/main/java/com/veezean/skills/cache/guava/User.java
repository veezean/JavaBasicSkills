package com.veezean.skills.cache.guava;

import lombok.Data;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2022/10/30
 */
@Data
public class User {
    private String userId;
    private String userName;
    private String department;

    public User(String userId) {
        this.userId = userId;
    }

    public User(String userId, String userName, String department) {
        this.userId = userId;
        this.userName = userName;
        this.department = department;
    }
}
