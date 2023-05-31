package com.orderingMinAppAip.model.userInfo;

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
 * 对应表名: user_info
 * @author 映射类自动生成器-LXY
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("user_info")
public class UserInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 自增ID */
	@ApiModelProperty(value = "自增ID")
    @TableId(type = IdType.AUTO)
	private Integer id;

	/** 昵称 */
	@ApiModelProperty(value = "昵称")
	private String name;


	/** 账号 */
	@ApiModelProperty(value = "账号")
	private String account;

	/** 密码 */
	@ApiModelProperty(value = "密码")
	private String password;

	/** token */
	@ApiModelProperty(value = "token")
	private String token;

	/** 头像 */
	@ApiModelProperty(value = "头像")
	private String head;

	/** 联系手机 */
	@ApiModelProperty(value = "联系手机")
	private String phone;

	/** 个人简介 */
	@ApiModelProperty(value = "个人简介")
	private String brief;

	/** 创建时间 */
	@ApiModelProperty(value = "创建时间")
	private Date creatorTime;

	/** 更新时间 */
	@ApiModelProperty(value = "更新时间")
	private Date upTime;

	/** 状态：（0：禁用  1：启动） */
	@ApiModelProperty(value = "状态：（0：禁用  1：启动）")
	private Integer status;

}