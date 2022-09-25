package com.veezean.skills.swagger;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 扩展swagger字段注解，用于关联取值枚举类，自动生成字段的枚举值
 *
 * @author 架构悟道
 * @since 2022-09-05
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiPropertyReference {
    // 接口文档上的显示的字段名称，不设置则使用field本来名称
    String name() default "";
    // 字段简要描述，可选
    String value() default "";
    // 标识字段是否必填
    boolean required() default  false;
    // 指定取值对应的枚举类
    Class<? extends Enum> referenceClazz();
}
