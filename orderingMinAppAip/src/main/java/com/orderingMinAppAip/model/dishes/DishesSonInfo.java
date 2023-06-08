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
 * 对应表名: dishes_son_info
 * @author 映射类自动生成器-LXY
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("dishes_son_info")
public class DishesSonInfo implements Serializable {

	/** 自增id */
	@ApiModelProperty(value = "自增id")
	@TableId(type = IdType.AUTO)
	private Integer id;

	/** 菜品ID */
	@ApiModelProperty(value = "菜品ID")
	private Integer dishesId;

	/** 食材ID */
	@ApiModelProperty(value = "食材ID")
	private Integer dishesSonId;

}