package com.orderingMinAppAip.model.dishes;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

/**
 * 数据库映射类
 * 对应表名: dishes
 * @author 映射类自动生成器-LXY
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("dishes")
public class Dishes implements Serializable {

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

}