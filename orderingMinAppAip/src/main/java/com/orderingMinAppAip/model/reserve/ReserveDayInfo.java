package com.orderingMinAppAip.model.reserve;

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
 * 对应表名: reserve_day_info
 * @author 映射类自动生成器-LXY
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("reserve_day_info")
public class ReserveDayInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 自增ID */
	@ApiModelProperty(value = "自增ID")
	@TableId(type = IdType.AUTO)
	private Integer id;

	/** 预定ID */
	@ApiModelProperty(value = "预定ID")
	private Integer reserveDayId;

	/** 类型( 1：早上，2：中午，3：晚上，4：夜宵 ) */
	@ApiModelProperty(value = "类型( 1：早上，2：中午，3：晚上，4：夜宵 )")
	private Integer type;

	/** 状态：（0：取消，1：准备中，2：进行中，3：完成） */
	@ApiModelProperty(value = "状态：（0：取消，1：准备中，2：进行中，3：完成）")
	private Integer status;

	/** 菜品ID */
	@ApiModelProperty(value = "菜品ID")
	private Integer dishesId;

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