package com.veezean.skills.test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * 敏感资源申请服务
 */
public class SensitiveResourceApplyService {

    /**
     * 白名单用户列表
     */
    private final HashSet<User> whitelistUsers;

    /**
     * 总请求次数计
     */
    private int totalRequestCount;

    /**
     * 构造函数，加载白名单用户列表
     *
     * @param whitelistUsers 白名单用户列表
     */
    public SensitiveResourceApplyService(HashSet<User> whitelistUsers) {
        this.whitelistUsers = whitelistUsers;
    }

    /**
     * 申请资源
     *
     * @param currentUser
     */
    public void applyResource(User currentUser) {
        // 记录总请求数
        totalRequestCount++;

        if (!whitelistUsers.contains(currentUser)) {
            return;
        }

        whitelistUsers.stream()
                .filter(user -> user.equals(currentUser))
                .findFirst()
                .ifPresent(user -> {
                    // 记录用户请求数+1
                    user.incrementRequestCount();
                    // 分配申请的资源给用户
                    doApplyResource(user.getApplyedResource());
                });
    }

    /**
     * 获取频繁访问申请接口的用户列表
     *
     * @return 符合条件的用户列表
     */
    public List<User> getFrequentApplyUsers() {
        List<User> users = new ArrayList<>(whitelistUsers);
        // 过滤出请求数大于100的用户列表
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getRequestCount() < 100) {
                users.remove(i);
            }
        }
        return users;
    }

    /**
     * 获取接口总请求次数
     *
     * @return 总请求次数
     */
    public int getTotalRequestCount() {
        return totalRequestCount;
    }

    /**
     * 分配资源操作
     *
     * @param resource 待申请的资源
     */
    private void doApplyResource(Resource resource) {
        if (resource == null) {
            resource = new Resource();
        }
        resource.setResourceName("VPN Account");
    }


}
