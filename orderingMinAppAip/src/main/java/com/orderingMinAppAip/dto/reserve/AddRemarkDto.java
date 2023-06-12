package com.orderingMinAppAip.dto.reserve;

import lombok.Data;

@Data
public class AddRemarkDto {
    private Integer familyId;
    private Integer reserveDayId;
    private String content;
    private Integer parentId;
}
