package com.veezean.skills.lock.cas;

import lombok.Data;

/**
 * <类功能简要描述>
 *
 * @author 架构悟道
 * @since 2022/7/28
 */
@Data
public class Item {
    private int id;
    private int version;
    private String content;
}
