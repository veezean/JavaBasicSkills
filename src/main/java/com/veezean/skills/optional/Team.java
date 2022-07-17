package com.veezean.skills.optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 团队信息
 *
 * @author 架构悟道
 * @since 2022/7/13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Team {
    private String teamName;
    private Department department;

    public Team(String teamName) {
        this.teamName = teamName;
    }
}
