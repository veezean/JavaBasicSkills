package com.veezean.skills.swagger;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * swagger自动生成枚举值描述的注解定义
 *
 * @author 架构悟道
 * @since 2022-09-05
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface SwaggerDisplayEnum {
    String value() default "value";
    String desc() default "desc";
}
