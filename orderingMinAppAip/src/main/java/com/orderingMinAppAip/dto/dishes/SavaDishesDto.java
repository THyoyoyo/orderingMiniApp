package com.orderingMinAppAip.dto.dishes;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.orderingMinAppAip.model.dishes.DishesComment;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SavaDishesDto {
    /** 自增ID */
    @ApiModelProperty(value = "自增ID")
    @TableId(type = IdType.AUTO)
    private Integer id;

    /** 家庭ID */
    @ApiModelProperty(value = "家庭ID")
    private Integer familyId;

    /** 菜名 */
    @ApiModelProperty(value = "菜名")
    private String name;

    /** 分类id */
    @ApiModelProperty(value = "分类id")
    private Integer dishesClassId;

    /** 简介 */
    @ApiModelProperty(value = "简介")
    private String brief;

    /** 是否公开（0：不公开，1：公开） */
    @ApiModelProperty(value = "是否公开（0：不公开，1：公开）")
    private Integer isPublic;

    /** 喜爱（0：未设置，1：设置） */
    @ApiModelProperty(value = "喜爱（0：未设置，1：设置）")
    private Integer userLike;

    /** 制作教程（流程） */
    @ApiModelProperty(value = "制作教程（流程）")
    private String course;

    /** 排序 */
    @ApiModelProperty(value = "排序")
    private Integer sort;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间")
    private Date creatorTime;

    /** 创建人 */
    @ApiModelProperty(value = "创建人")
    private String creatorName;


    /** 创建人ID */
    @ApiModelProperty(value = "创建人ID")
    private Integer creatorUserId;


    /** 难度等级（1：简单，2：一般，3:困难） */
    @ApiModelProperty(value = "难度等级（1：简单，2：一般，3:困难）")
    private Integer difficulty;


    /** 预计耗时 */
    @ApiModelProperty(value = "预计耗时")
    private Integer timeConsuming;

    @ApiModelProperty(value = "图片组")
    private List<String> imgList;

    @ApiModelProperty(value = "食材组")
    private List<String> dishesSonList;


    @ApiModelProperty(value = "评论列表",hidden = false,required = false)
    private  List<DishesComment> dishesComments;
}
