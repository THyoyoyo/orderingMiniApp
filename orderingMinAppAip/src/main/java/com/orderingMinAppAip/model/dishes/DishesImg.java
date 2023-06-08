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

/**
 * 数据库映射类
 * 对应表名: dishes_img
 * @author 映射类自动生成器-LXY
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("dishes_img")
public class DishesImg implements Serializable {

	/** 自增ID */
	@ApiModelProperty(value = "自增ID")
	@TableId(type = IdType.AUTO)
	private Integer id;

	/** 图片地址 */
	@ApiModelProperty(value = "图片地址")
	private String img;

	/** 菜品ID */
	@ApiModelProperty(value = "菜品ID")
	private Integer dishesId;
}