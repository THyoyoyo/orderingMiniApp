package com.orderingMinAppAip.service;

import com.orderingMinAppAip.model.reserve.ReserveDayInfo;

import java.util.List;

public interface ReserveService {
    List<ReserveDayInfo> delByIdInfo(Integer id);


    void addDishesInfo(Integer dishesId, Integer type, Integer userId, String userName, Integer reserveDayId, ReserveDayInfo oldReserveDayInfo);
}
