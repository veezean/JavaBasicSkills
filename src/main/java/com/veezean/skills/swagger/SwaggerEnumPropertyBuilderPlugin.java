package com.veezean.skills.swagger;

import cn.hutool.core.util.ReflectUtil;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.ModelPropertyBuilderPlugin;
import springfox.documentation.spi.schema.contexts.ModelPropertyContext;
import springfox.documentation.spi.service.ParameterBuilderPlugin;
import springfox.documentation.spi.service.contexts.ParameterContext;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 定制swagger生成逻辑，自动使用关联的枚举值进行生成doc
 *
 * @author 架构悟道
 * @since 2022-09-05
 */
@Component
@Slf4j
@Primary
public class SwaggerEnumPropertyBuilderPlugin implements  ModelPropertyBuilderPlugin, ParameterBuilderPlugin {

    @Override
    public void apply(ModelPropertyContext modelPropertyContext) {

        try {

            if (!modelPropertyContext.getBeanPropertyDefinition().isPresent()) {
                return;
            }

            BeanPropertyDefinition beanPropertyDefinition = modelPropertyContext.getBeanPropertyDefinition().get();
            AnnotatedField beanPropertyDefinitionField = beanPropertyDefinition.getField();
            if (beanPropertyDefinitionField == null) {
                return;
            }

            // 没有@ApiModelPropertyReference， 直接返回
            ApiPropertyReference propertyReference =
                    beanPropertyDefinitionField.getAnnotation(ApiPropertyReference.class);
            if (propertyReference == null) {
                return;
            }

            // 生成需要拼接的取值含义描述内容
            String valueDesc = generateValueDesc(propertyReference);
            modelPropertyContext.getBuilder().description(valueDesc)
                    .type(modelPropertyContext.getResolver()
                            .resolve(beanPropertyDefinition.getField().getRawType()));
        } catch (Exception e) {
            log.error("failed to rebuild swagger param description", e);
        }
    }

    @Override
    public boolean supports(DocumentationType documentationType) {
        return true;
    }

    @Override
    public void apply(ParameterContext context) {
        ApiPropertyReference reference =
                context.getOperationContext().findAnnotation(ApiPropertyReference.class).orNull();
        String desc = generateValueDesc(reference);
        if (StringUtils.isNotEmpty(reference.name())) {
            context.parameterBuilder().name(reference.name());
        }
        context.parameterBuilder().description(desc);
//        AllowableListValues allowableListValues = getAllowValues(reference);
//        context.parameterBuilder().allowableValues(allowableListValues);
    }

    private String generateValueDesc(ApiPropertyReference propertyReference) {
        Class<? extends Enum> rawPrimaryType = propertyReference.referenceClazz();
        SwaggerDisplayEnum swaggerDisplayEnum = AnnotationUtils.findAnnotation(rawPrimaryType,
                SwaggerDisplayEnum.class);
        String enumFullDesc = Arrays.stream(rawPrimaryType.getEnumConstants())
                .filter(Objects::nonNull)
                .map(enumConsts -> {
                    Object fieldValue = ReflectUtil.getFieldValue(enumConsts, swaggerDisplayEnum.value());
                    Object fieldDesc = ReflectUtil.getFieldValue(enumConsts, swaggerDisplayEnum.desc());
                    return fieldValue + ":" + fieldDesc;
                }).collect(Collectors.joining(";"));
        return propertyReference.value() + "(" + enumFullDesc + ")";
    }
}
