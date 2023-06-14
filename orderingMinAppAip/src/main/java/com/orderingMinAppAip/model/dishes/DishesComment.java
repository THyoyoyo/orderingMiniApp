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
 * 对应表名: dishes_comment
 * @author 映射类自动生成器-LXY
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("dishes_comment")
public class DishesComment implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 自增ID */
	@ApiModelProperty(value = "自增ID")
	@TableId(type = IdType.AUTO)
	private Integer id;

	/** 菜品ID */
	@ApiModelProperty(value = "菜品ID")
	private Integer dishesId;

	/** 评论内容 */
	@ApiModelProperty(value = "评论内容")
	private String content;

	/** 状态（0：隐藏，1：显示） */
	@ApiModelProperty(value = "状态（0：隐藏，1：显示）")
	private Integer status;



	/** 星级（0-5） */
	@ApiModelProperty(value = "星级（0-5）")
	private Integer starLevel;

	/** 创建时间 */
	@ApiModelProperty(value = "创建时间")
	private Date creatorTime;

	/** 创建人 */
	@ApiModelProperty(value = "创建人")
	private String creatorUser;

	/** 创建人ID */
	@ApiModelProperty(value = "创建人ID")
	private Integer creatorUserId;



}