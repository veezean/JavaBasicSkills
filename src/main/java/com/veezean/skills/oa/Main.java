package com.veezean.skills.oa;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2022/10/21
 */
public class Main {

    public static void main(String[] args) {
        User user = new User();
        user.setId(12);
        user.setAccount("lanqiao");
        user.setUserName("蓝桥");
        user.setPassword("123456");
        user.setPhone("13900000000");
        user.setSex(1);
//        user.setDepartment(23);
//        user.setRoles(Stream.of(1,2,3).collect(Collectors.toList()));
        user.setDepartmentName("研发部");
        user.setRoles(Stream.of(new Role(1, "普通用户")).collect(Collectors.toList()));

        String s = JSON.toJSONString(user);
        System.out.println(s);

        Response<User> response = new Response<>();
        response.setCode("0000");
        response.setSuccess(true);
        response.setMsg("用户创建成功");
        response.setData(user);
        System.out.println(JSON.toJSONString(response));

        LoginReq req = new LoginReq();
        req.setAccount("lanqiao");
        req.setPassword("123456");
        System.out.println(JSON.toJSONString(req));


        ClockInRecord record = new ClockInRecord();
        record.setClockinTime(new Date());
        record.setLocation("三里屯");
        record.setType(1);
        record.setUserId(12);

        System.out.println(JSON.toJSONString(record));


        Attendance attendance = new Attendance();
        attendance.setAttendanceDate("2022-10-18");
        attendance.setFirstClockIn("2022-10-18 08:22:23");
        attendance.setLastClockIn("2022-10-18 18:12:34");
        attendance.setStatus(1);

        Response<Attendance> response2 = new Response<>();
        response2.setCode("0000");
        response2.setSuccess(true);
        response2.setMsg("操作成功");
        response2.setData(attendance);
        System.out.println(JSON.toJSONString(response2));

        Notice notice = new Notice();
        notice.setAuthor("蓝桥");
        notice.setId(2345);
        notice.setNoticeTitle("关于元旦放假的通知");
        notice.setNoticeDetail("各位同事，遵照国家相关规定，拟定于从2023年01月01日至2023年01月03日为元旦假期，期间公司主要办公场所不开门。祝大家元旦快乐。");
        notice.setPublishTime("2022-10-18 18:12:34");

        Response<Notice> response3 = new Response<>();
        response3.setCode("0000");
        response3.setSuccess(true);
        response3.setMsg("查询成功");
        response3.setData(notice);
        System.out.println(JSON.toJSONString(response3));
        System.out.println();

        Response<List<Notice>> resp = new Response<>();
        resp.setCode("0000");
        resp.setSuccess(true);
        resp.setMsg("查询成功");

        List<Notice> notices = new ArrayList<>();
        notices.add(notice);
        resp.setData(notices);
        System.out.println(JSON.toJSONString(resp));
    }
}
