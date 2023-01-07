package com.veezean.skills.cache.guava;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2022/10/30
 */
public class UserDao {
    public User getUser(String userId) {
        // 辅助测试类
        if ("123".equals(userId)) {
            return new User("123", "铁柱", "研发部");
        } else if ("124".equals(userId)) {
            return new User("124", "翠花", "测试部");
        } else if ("125".equals(userId)) {
            return new User("125", "阿牛", "市场部");
        } else {
            return null;
        }
    }
}
