package com.orderingMinAppAip.vo.reserve;


import lombok.Data;

import java.util.Date;

@Data
public class reserveInfoItemVo {
    private Integer familyId;
    private String dishesName;
    private Date creator;
    private Integer reserveDayId;
    private Integer dishesId;
    private Integer id;
    private Integer type;
    private Integer status;
    private Date creatorTime;
    private String userName;
}
