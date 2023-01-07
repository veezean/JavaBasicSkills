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
public class Notice {
    private int id;
    private String noticeTitle;
    private String noticeDetail;
    private String author;
    private String publishTime;
}
