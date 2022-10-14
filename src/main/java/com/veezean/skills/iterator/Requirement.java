package com.veezean.skills.iterator;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2022/9/20
 */
@Data
public class Requirement {
    private List<Task> tasks;
    private int status;
    private String requirementName;
    private Date createTime;
    private Date closeTime;
    // ...
}
