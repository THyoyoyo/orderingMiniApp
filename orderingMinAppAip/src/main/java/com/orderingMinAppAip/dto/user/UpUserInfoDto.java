package com.orderingMinAppAip.dto.user;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class UpUserInfoDto {

    /** 昵称 */
    @ApiModelProperty(value = "昵称")
    private String name;

    /** 头像 */
    @ApiModelProperty(value = "头像")
    private String head;

    /** 联系手机 */
    @ApiModelProperty(value = "联系手机")
    private String phone;

    /** 个人简介 */
    @ApiModelProperty(value = "个人简介")
    private String brief;
}
