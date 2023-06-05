package com.orderingMinAppAip.dto.family;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UpFamilyUserRemarkDto {
    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "家庭Id")
    private Integer familyId;


    @ApiModelProperty(value = "备注昵称")
    private String userRemark;


}
