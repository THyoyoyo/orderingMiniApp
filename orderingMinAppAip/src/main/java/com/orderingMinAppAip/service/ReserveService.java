package com.orderingMinAppAip.service;

import com.orderingMinAppAip.model.family.FamilyMember;
import com.orderingMinAppAip.model.reserve.ReserveDayInfo;
import com.orderingMinAppAip.model.reserve.ReserveDayRemark;
import com.orderingMinAppAip.vo.reserve.ReserveInfoVo;

import java.util.Date;
import java.util.List;

public interface ReserveService {

    List<ReserveDayInfo> delByIdInfo(Integer id);

    void addDishesInfo(Integer dishesId, Integer type, Integer userId, String userName, Integer reserveDayId, ReserveDayInfo oldReserveDayInfo);

    ReserveInfoVo getByIdInfo(Integer familyId, Integer id, FamilyMember joinFamily) throws Exception;

    List<ReserveDayRemark> getByIdRemarks(Integer reserveDayId);

    Integer isDayReserver(Date time ,Integer familyId);
}
