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
 * 对应表名: reserve_day_remark
 * @author 映射类自动生成器-LXY
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("reserve_day_remark")
public class ReserveDayRemark implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 自增ID */
	@ApiModelProperty(value = "自增ID")
    @TableId(type = IdType.AUTO)
	private Integer id;

	/** 预定ID */
	@ApiModelProperty(value = "预定ID")
	private Integer reserveDayId;

	/** 备注内容 */
	@ApiModelProperty(value = "备注内容")
	private String content;

	/** 回复评论ID */
	@ApiModelProperty(value = "回复评论ID")
	private Integer parentId;

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