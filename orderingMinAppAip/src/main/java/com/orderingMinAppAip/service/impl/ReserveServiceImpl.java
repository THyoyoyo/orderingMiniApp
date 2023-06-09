package com.orderingMinAppAip.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.orderingMinAppAip.mapper.reserve.ReserveDayInfoMapper;
import com.orderingMinAppAip.mapper.reserve.ReserveDayMapper;
import com.orderingMinAppAip.model.reserve.ReserveDayInfo;
import com.orderingMinAppAip.service.ReserveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ReserveServiceImpl implements ReserveService {

    @Autowired
    ReserveDayInfoMapper reserveDayInfoMapper;

    @Autowired
    ReserveDayMapper reserveDayMapper;

    @Override
    public List<ReserveDayInfo> delByIdInfo(Integer id) {
        QueryWrapper<ReserveDayInfo> reserveDayInfoQueryWrapper = new QueryWrapper<>();
        reserveDayInfoQueryWrapper.eq("reserve_day_id",id);
        List<ReserveDayInfo> reserveDayInfos = reserveDayInfoMapper.selectList(reserveDayInfoQueryWrapper);
        reserveDayInfoMapper.delete(reserveDayInfoQueryWrapper);
        return reserveDayInfos;
    }

    @Override
    public void addDishesInfo(Integer dishesId, Integer type, Integer userId, String userName, Integer reserveDayId, ReserveDayInfo oldReserveDayInfo) {
        ReserveDayInfo reserveDayInfo = new ReserveDayInfo();
        if(oldReserveDayInfo !=null){
            reserveDayInfoMapper.insert(oldReserveDayInfo);
        }else {
            reserveDayInfo.setDishesId(dishesId);
            reserveDayInfo.setReserveDayId(reserveDayId);
            reserveDayInfo.setCreatorTime(new Date());
            reserveDayInfo.setType(type);
            reserveDayInfo.setStatus(1);
            reserveDayInfo.setCreatorUserId(userId);
            reserveDayInfo.setCreatorUser(userName);
            reserveDayInfoMapper.insert(reserveDayInfo);
        }
    }
}
