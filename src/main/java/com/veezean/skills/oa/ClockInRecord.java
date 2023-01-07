package com.veezean.skills.oa;

import lombok.Data;

import java.util.Date;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2022/10/21
 */
@Data
public class ClockInRecord {
    private int id;
    private int userId;
    private int type;
    private Date clockinTime;
    private String location;
}
