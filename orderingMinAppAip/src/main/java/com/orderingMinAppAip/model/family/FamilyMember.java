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
 * 对应表名: family_member
 * @author 映射类自动生成器-LXY
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("family_member")
public class FamilyMember implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 自增ID */
	@ApiModelProperty(value = "自增ID")
    @TableId(type = IdType.AUTO)
	private Integer id;

	/** 家庭ID */
	@ApiModelProperty(value = "家庭ID")
	private Integer familyId;

	/** 成员ID */
	@ApiModelProperty(value = "成员ID")
	private Integer memberId;

	/** 身份（1：户主，2：成员，3：访客） */
	@ApiModelProperty(value = "身份（1：户主，2：成员，3：访客）")
	private Integer identityType;

	/** 加入时间 */
	@ApiModelProperty(value = "加入时间")
	private Date creatorTime;

}