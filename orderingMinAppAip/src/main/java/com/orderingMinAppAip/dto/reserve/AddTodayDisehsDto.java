package com.orderingMinAppAip.dto.reserve;


import lombok.Data;

@Data
public class AddTodayDisehsDto {
    private Integer familyId;

    private Integer reserveDayId;

    private Integer type;

    private Integer dishesId;
}
