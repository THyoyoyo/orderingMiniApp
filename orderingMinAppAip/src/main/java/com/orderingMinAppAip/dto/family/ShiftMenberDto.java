package com.orderingMinAppAip.dto.family;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ShiftMenberDto {
    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "家庭Id")
    private Integer familyId;
}
