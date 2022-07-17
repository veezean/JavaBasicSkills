package com.veezean.skills.stream.collect;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.*;
import java.util.stream.Collector;

/**
 * 自定义收集器
 *
 * 对Stream中的数字字段，先平方再累加，得到总累加值
 *
 * @author 架构悟道
 * @since 2022/7/16
 */
public class MyCollector<T> implements Collector<T, AtomicInteger, Integer> {

    private ToIntFunction<T> mapper;

    public MyCollector(ToIntFunction<T> mapper) {
        this.mapper = mapper;
    }

    @Override
    public Supplier<AtomicInteger> supplier() {
        // 指定用于最终结果的收集，此处返回new AtomicInteger(0)，后续在此基础上累加
        return () -> new AtomicInteger(0);
    }

    @Override
    public BiConsumer<AtomicInteger, T> accumulator() {
        // 每个元素进入的时候的遍历策略，当前元素值的平方与sum结果进行累加
        return (sum, current) -> {
            int intValue = mapper.applyAsInt(current);
            sum.addAndGet(intValue * intValue);
        };
    }

    @Override
    public BinaryOperator<AtomicInteger> combiner() {
        // 多个分段结果处理的策略，直接相加
        return (sum1, sum2) -> {
            sum1.addAndGet(sum2.get());
            return sum1;
        };
    }

    @Override
    public Function<AtomicInteger, Integer> finisher() {
        // 结果处理完成之后对结果的二次处理
        // 为了支持多线程并发处理，此处内部使用了AtomicInteger作为了结果累加器
        // 但是收集器最终需要返回Integer类型值，此处进行对结果的转换
        return AtomicInteger::get;
    }

    @Override
    public Set<Characteristics> characteristics() {
        Set<Characteristics> characteristics = new HashSet<>();
        // 指定该收集器支持并发处理（前面也发现我们采用了线程安全的AtomicInteger方式）
        characteristics.add(Characteristics.CONCURRENT);
        // 声明元素数据处理的先后顺序不影响最终收集的结果
        characteristics.add(Characteristics.UNORDERED);
        // 注意:这里没有添加下面这句，因为finisher方法对结果进行了处理，非恒等转换
        // characteristics.add(Characteristics.IDENTITY_FINISH);
        return characteristics;
    }
}
