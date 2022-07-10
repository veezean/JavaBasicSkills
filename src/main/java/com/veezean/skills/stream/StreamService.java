package com.veezean.skills.stream;

import com.sun.deploy.util.ArrayUtil;
import com.sun.istack.internal.NotNull;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * JAVA Stream操作实现类
 *
 * @author 架构悟道
 * @since 2022/7/9
 */
public class StreamService {

    /**
     * 【常规方式】 从给定句子中返回单词长度大于5的单词列表，按长度倒序输出，最多返回3个
     *
     * @param sentence 给定的句子，约定非空，且单词之间仅由一个空格分隔
     * @return 倒序输出符合条件的单词列表
     */
    public List<String> sortGetTop3LongWords(@NotNull String sentence) {
        // 先切割句子，获取具体的单词信息
        String[] words = sentence.split(" ");
        List<String> wordList = new ArrayList<>();
        // 循环判断单词的长度，先过滤出符合长度要求的单词
        for (String word : words) {
            if (word.length() > 5) {
                wordList.add(word);
            }
        }
        // 对符合条件的列表按照长度进行排序
        wordList.sort((o1, o2) -> o2.length() - o1.length());
        // 判断list结果长度，如果大于3则截取前三个数据的子list返回
        if (wordList.size() > 3) {
            wordList = wordList.subList(0, 3);
        }
        return wordList;
    }

    /**
     * 【Stream方式】 从给定句子中返回单词长度大于5的单词列表，按长度倒序输出，最多返回3个
     *
     * @param sentence 给定的句子，约定非空，且单词之间仅由一个空格分隔
     * @return 倒序输出符合条件的单词列表
     */
    public List<String> sortGetTop3LongWordsByStream(@NotNull String sentence) {
        return Arrays.stream(sentence.split(" "))
                .filter(word -> word.length() > 5)
                .sorted((o1, o2) -> o2.length() - o1.length())
                .limit(3)
                .collect(Collectors.toList());
    }

    /**
     * 演示map的用途：一对一转换
     */
    public void stringToIntMap() {
        List<String> ids = Arrays.asList("205", "105", "308", "469", "627", "193", "111");
        // 使用流操作
        List<User> results = ids.stream()
                .map(id -> {
                    User user = new User();
                    user.setId(id);
                    return user;
                })
                .collect(Collectors.toList());
        System.out.println(results);
    }

    /**
     * 演示flatMap的用途：一对多转换
     */
    public void stringToIntFlatmap() {
        List<String> sentences = Arrays.asList("hello world", "Jia Gou Wu Dao");
        // 使用流操作
        List<String> results = sentences.stream()
                .flatMap(sentence -> Arrays.stream(sentence.split(" ")))
                .collect(Collectors.toList());
        System.out.println(results);
    }

    /**
     * 演示peek与foreach的用途区别
     */
    public void testPeekAndforeach() {
        List<String> sentences = Arrays.asList("hello world", "Jia Gou Wu Dao");
        // 演示点1： 仅peek操作，最终不会执行
        System.out.println("----before peek----");
        sentences.stream().peek(sentence -> System.out.println(sentence));
        System.out.println("----after peek----");
        // 演示点2： 仅foreach操作，最终会执行
        System.out.println("----before foreach----");
        sentences.stream().forEach(sentence -> System.out.println(sentence));
        System.out.println("----after foreach----");
        // 演示点3： peek操作后面增加终止操作，peek会执行
        System.out.println("----before peek and count----");
        sentences.stream().peek(sentence -> System.out.println(sentence)).count();
        System.out.println("----after peek and count----");
    }


    /**
     * 演示一个或者多个中间操作组合使用的情况
     */
    public void testGetTargetUsers() {
        List<String> ids = Arrays.asList("205", "10", "308", "49", "627", "193", "111", "193");
        // 使用流操作
        List<Dept> results = ids.stream()
                .filter(s -> s.length() > 2)
                .distinct()
                .map(Integer::valueOf)
                .sorted(Comparator.comparingInt(o -> o))
                .limit(3)
                .map(id -> new Dept(id))
                .collect(Collectors.toList());
        System.out.println(results);
    }

    /**
     * 演示简单终止操作
     */
    public void testSimpleStopOptions() {
        List<String> ids = Arrays.asList("205", "10", "308", "49", "627", "193", "111", "193");

        // 统计stream操作后剩余的元素个数
        System.out.println(ids.stream().filter(s -> s.length() > 2).count());
        // 判断是否有元素值等于205
        System.out.println(ids.stream().filter(s -> s.length() > 2).anyMatch("205"::equals));
        // findFirst操作
        ids.stream().filter(s -> s.length() > 2)
                .findFirst()
                .ifPresent(s -> System.out.println("findFirst:" + s));
    }


    /**
     * 演示collect终止操作
     */
    public void testCollectStopOptions() {
        List<Dept> ids = Arrays.asList(new Dept(17), new Dept(22), new Dept(23));

        // collect成list
        List<Dept> collectList = ids.stream().filter(dept -> dept.getId() > 20)
                .collect(Collectors.toList());
        System.out.println("collectList:" + collectList);

        // collect成Set
        Set<Dept> collectSet = ids.stream().filter(dept -> dept.getId() > 20)
                .collect(Collectors.toSet());
        System.out.println("collectSet:" + collectSet);

        // collect成HashMap，key为id，value为Dept对象
        Map<Integer, Dept> collectMap = ids.stream().filter(dept -> dept.getId() > 20)
                .collect(Collectors.toMap(Dept::getId, dept -> dept));
        System.out.println("collectMap:" + collectMap);
    }

    /**
     * 演示stream被执行过终止操作之后，便不能再对其进行操作了，否则会报错
     */
    public void testHandleStreamAfterClosed() {
        List<String> ids = Arrays.asList("205", "10", "308", "49", "627", "193", "111", "193");
        Stream<String> stream = ids.stream().filter(s -> s.length() > 2);

        // 统计stream操作后剩余的元素个数
        System.out.println(stream.count());
        System.out.println("-----下面会报错-----");
        // 判断是否有元素值等于205
        try {
            System.out.println(stream.anyMatch("205"::equals));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("-----上面会报错-----");
    }

    /**
     * 演示不用stream将list中元素拼接为一个以逗号分隔的字符串的做法
     */
    public void testForJoinStrings() {
        List<String> ids = Arrays.asList("205", "10", "308", "49", "627", "193", "111", "193");
        StringBuilder builder = new StringBuilder();
        for (String id : ids) {
            builder.append(id).append(',');
        }
        // 去掉末尾多拼接的逗号
        builder.deleteCharAt(builder.length() - 1);
        System.out.println("拼接后：" + builder.toString());
    }

    /**
     * 演示使用stream将list中元素拼接为一个以逗号分隔的字符串的做法
     */
    public void testCollectJoinStrings() {
        List<String> ids = Arrays.asList("205", "10", "308", "49", "627", "193", "111", "193");
        String joinResult = ids.stream().collect(Collectors.joining(","));
        System.out.println("拼接后：" + joinResult);
    }

    /**
     * 演示使用stream进行数字的计算
     */
    public void testNumberCalculate() {
        List<Integer> ids = Arrays.asList(10, 20, 30, 40, 50);
        // 计算平均值
        Double average = ids.stream().collect(Collectors.averagingInt(value -> value));
        System.out.println("平均值：" + average);
        // 数据统计信息
        IntSummaryStatistics summary = ids.stream().collect(Collectors.summarizingInt(value -> value));
        System.out.println("数据统计信息： " + summary);
    }

    public static void main(String[] args) {
        String sentence = "hello everybody this is JiaGouWuDao and he has many software technology experiences want " +
                "to" +
                " show you";
        System.out.println(sentence);
        StreamService streamService = new StreamService();
        System.out.println("使用常规方法：");
        System.out.println(streamService.sortGetTop3LongWords(sentence));
        System.out.println("使用Stream方法：");
        System.out.println(streamService.sortGetTop3LongWordsByStream(sentence));

        streamService.stringToIntMap();
        streamService.stringToIntFlatmap();
        streamService.testPeekAndforeach();
        streamService.testGetTargetUsers();
        streamService.testSimpleStopOptions();
        streamService.testCollectStopOptions();
        streamService.testHandleStreamAfterClosed();
        streamService.testForJoinStrings();
        streamService.testCollectJoinStrings();
        streamService.testNumberCalculate();


    }

}
