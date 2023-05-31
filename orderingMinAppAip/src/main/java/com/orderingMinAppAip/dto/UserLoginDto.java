package com.orderingMinAppAip.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserLoginDto {


    /** 账号 */
    @ApiModelProperty(value = "账号")
    private String account;

    /** 密码 */
    @ApiModelProperty(value = "密码")
    private String password;


}
