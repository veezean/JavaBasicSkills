package com.veezean.skills.iterator;

import lombok.Data;

import java.util.List;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2022/9/20
 */
@Data
public class Project {
    private List<Requirement> requirements;
    private int status;
    private String projectName;
    // ...
}