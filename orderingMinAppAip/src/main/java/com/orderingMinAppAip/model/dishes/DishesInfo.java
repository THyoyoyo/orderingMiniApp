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
 * 对应表名: dishes_info
 * @author 映射类自动生成器-LXY
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("dishes_info")
public class DishesInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 自增ID */
	@ApiModelProperty(value = "自增ID")
	@TableId(type = IdType.AUTO)
	private Integer id;

	/** 菜品ID */
	@ApiModelProperty(value = "菜品ID")
	private Integer dishesId;

	/** 分类ID */
	@ApiModelProperty(value = "分类ID")
	private Integer dishesClassId;

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