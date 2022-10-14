package com.veezean.skills.swagger;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <类功能简要描述>
 *
 * @author 架构悟道
 * @since 2022/9/4
 */
@Data
@ApiModel("操作记录信息")
public class OperateLog {
    @ApiModelProperty(value = "记录唯一ID，无实际意义", hidden = true)
    private long id;
    @ApiModelProperty("操作类型，取值说明： 1，新增；2，更新；3，删除；4，查询")
    private int operateType;
    @ApiModelProperty("操作用户")
    private String user;
    @ApiModelProperty(value = "操作详情")
    private String detail;
}
