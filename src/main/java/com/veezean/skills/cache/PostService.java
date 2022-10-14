package com.veezean.skills.cache;

import java.util.concurrent.TimeUnit;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2022/10/13
 */
public class PostService {

    private DemoCache<String, PostInfo> cache = new DemoCache<>();


    public void sendPost(PostInfo post) {
        // ... 省略业务逻辑细节
        // 将新创建的帖子加入缓存中，缓存3分钟
        cache.put(post.getId(), post, 3, TimeUnit.MINUTES);
    }

    public PostInfo getPost(String postId) {
        PostInfo postInfo = cache.get(postId);
        if (postInfo != null) {
            // 每次使用后，都重新设置过期时间为3分钟后（续期）
            cache.expireAfter(postId, 3, TimeUnit.MINUTES);
        }
        return postInfo;
    }
}
