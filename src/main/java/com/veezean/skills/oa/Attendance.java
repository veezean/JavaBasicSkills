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
public class Attendance {
    private int id;
    private int userId;
    private String attendanceDate;
    private String firstClockIn;
    private String lastClockIn;
    private int status;
}
