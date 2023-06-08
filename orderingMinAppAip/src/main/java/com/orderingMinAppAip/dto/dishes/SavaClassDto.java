package com.orderingMinAppAip.dto.dishes;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SavaClass {

    /** 自增id */
    @ApiModelProperty(value = "自增id")

    private Integer id;

    /** 家庭ID */
    @ApiModelProperty(value = "家庭ID")
    private Integer familyId;

    /** 状态 0：禁用 1：启用 */
    @ApiModelProperty(value = "状态 0：禁用 1：启用")
    private Integer status;

    /** 分类名称 */
    @ApiModelProperty(value = "分类名称")
    private String name;

}
