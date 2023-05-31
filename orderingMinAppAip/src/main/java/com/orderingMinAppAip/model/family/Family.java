package com.orderingMinAppAip.model.family;

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
 * 对应表名: family
 * @author 映射类自动生成器-LXY
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("family")
public class Family implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 自增ID */
	@ApiModelProperty(value = "自增ID")
	@TableId(type = IdType.AUTO)
	private Integer id;

	/** 家庭名称 */
	@ApiModelProperty(value = "家庭名称")
	private String name;

	/** 地址 */
	@ApiModelProperty(value = "地址")
	private String address;

	/** 头像URL */
	@ApiModelProperty(value = "头像URL")
	private String head;

	/** 其他信息 */
	@ApiModelProperty(value = "其他信息")
	private String otherInfo;

	/** 邀请码 */
	@ApiModelProperty(value = "邀请码")
	private Integer invitationCode;

	/** 创建者 */
	@ApiModelProperty(value = "创建者")
	private Integer creatorUserId;

	/** 创建时间 */
	@ApiModelProperty(value = "创建时间")
	private Date creatorTime;

}