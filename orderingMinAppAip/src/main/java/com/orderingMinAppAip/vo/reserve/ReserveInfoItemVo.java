package com.orderingMinAppAip.vo.reserve;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class ReserveInfoItemVo {
    private Integer familyId;
    private String dishesName;
    private Date creator;
    private Integer reserveDayId;
    private Integer dishesId;
    private Integer id;
    private Integer type;
    private Integer status;
    private Integer creatorUserId;
    private Date creatorTime;
    private String userName;
    @ApiModelProperty("是否可编辑（1：可编辑，0：不可编辑）")
    private Integer isEdit;
}
