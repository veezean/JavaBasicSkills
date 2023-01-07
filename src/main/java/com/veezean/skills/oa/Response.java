package com.veezean.skills.oa;

import lombok.Data;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2022/10/21
 */
@Data
public class Response<T> {
    private String code;
    private boolean success;
    private String msg;
    private T data;

}
