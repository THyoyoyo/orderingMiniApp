package com.orderingMinAppAip.dto.family;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

@Data
public class FamilyDto implements Serializable {
	/** 自增ID */
	@ApiModelProperty(value = "自增ID")
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
	@ApiModelProperty(value = "成员-邀请码")
	private Integer invitationCode;


	/** 访客-邀请码 */
	@ApiModelProperty(value = "访客-邀请码")
	private Integer guestInvitationCode;

}