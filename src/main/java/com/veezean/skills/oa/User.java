package com.veezean.skills.oa;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2022/10/21
 */
@Data
public class User {
    private int id;
    private String account;
    private String userName;
    private String password;
    private int sex;
    private String phone;
    private List<Role> roles;
//    private List<Integer> roles;
//    private int department;
    private String departmentName;
}
