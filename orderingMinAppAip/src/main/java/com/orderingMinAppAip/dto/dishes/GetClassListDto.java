package com.orderingMinAppAip.dto.dishes;


import lombok.Data;

@Data
public class GetClassListDto {

    private  Integer pageNum;
    private  Integer pageSize;
    private  Integer dishesClassId;
    private  Integer familyId;

}
