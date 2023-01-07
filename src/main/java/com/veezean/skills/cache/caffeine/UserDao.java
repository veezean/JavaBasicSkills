package com.veezean.skills.cache.caffeine;

import com.veezean.skills.cache.guava.User;

import java.util.ArrayList;
import java.util.List;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2022/11/12
 */
public class UserDao {

    public User getUser(String key) {
        return new User(key, key);
    }


 }
