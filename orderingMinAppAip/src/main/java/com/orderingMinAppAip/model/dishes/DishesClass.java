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
 * 对应表名: dishes_class
 * @author 映射类自动生成器-LXY
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("dishes_class")
public class DishesClass implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 自增id */
	@ApiModelProperty(value = "自增id")

	@TableId(type= IdType.AUTO)
	private Integer id;

	/** 家庭ID */
	@ApiModelProperty(value = "家庭ID")
	private Integer familyId;

	/** 分类名称 */
	@ApiModelProperty(value = "分类名称")
	private String name;

	/** 状态 0：禁用 1：启用 */
	@ApiModelProperty(value = "状态 0：禁用 1：启用")
	private Integer status;


	/** 创建时间 */
	@ApiModelProperty(value = "创建时间")
	private Date creatorTime;

	/** 创建人 */
	@ApiModelProperty(value = "创建人")
	private String creatorName;

	/** 创建人ID */
	@ApiModelProperty(value = "创建人ID")
	private Integer creatorUserId;

}