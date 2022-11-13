package com.veezean.skills.cache.guava;

import lombok.Data;
import lombok.ToString;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2022/11/6
 */
@Data
@ToString
public class User {
    private String userName;
    private String userId;
    private String departmentId;

    public User(String userId) {
        this.userId = userId;
    }

    public User(String userId, String userName) {
        this.userName = userName;
        this.userId = userId;
    }
}
