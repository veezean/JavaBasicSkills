package com.veezean.skills.cache.framework;

import lombok.Data;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2022/10/16
 */
@Data
public class User {
    private String userName;

    public User(String userName) {
        this.userName = userName;
    }
}
