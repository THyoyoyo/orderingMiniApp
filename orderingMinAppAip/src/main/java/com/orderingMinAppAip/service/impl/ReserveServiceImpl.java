package com.orderingMinAppAip.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.orderingMinAppAip.enums.ReserveTimeTypeEnum;
import com.orderingMinAppAip.mapper.reserve.ReserveDayInfoMapper;
import com.orderingMinAppAip.mapper.reserve.ReserveDayMapper;
import com.orderingMinAppAip.mapper.reserve.ReserveDayRemarkMapper;
import com.orderingMinAppAip.model.family.FamilyMember;
import com.orderingMinAppAip.model.reserve.ReserveDay;
import com.orderingMinAppAip.model.reserve.ReserveDayInfo;
import com.orderingMinAppAip.model.reserve.ReserveDayRemark;
import com.orderingMinAppAip.service.ReserveService;
import com.orderingMinAppAip.util.CurrentUserUtil;
import com.orderingMinAppAip.vo.reserve.ReserveInfoItemVo;
import com.orderingMinAppAip.vo.reserve.ReserveInfoVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ReserveServiceImpl implements ReserveService {

    @Autowired
    ReserveDayInfoMapper reserveDayInfoMapper;

    @Autowired
    ReserveDayMapper reserveDayMapper;

    @Autowired
    ReserveDayRemarkMapper reserveDayRemarkMapper;

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

    @Override
    public ReserveInfoVo getByIdInfo(Integer familyId, Integer id, FamilyMember joinFamily) throws Exception {
        Integer userId = CurrentUserUtil.getUserId();

        ReserveDay reserveDay = reserveDayMapper.selectById(id);
        List<ReserveInfoItemVo> byReserveDayIdList = null;
        if(joinFamily != null){
              byReserveDayIdList =reserveDayInfoMapper.getByReserveDayIdList(id,joinFamily.getIdentityType(),userId);
        }else {
            byReserveDayIdList =reserveDayInfoMapper.getByReserveDayIdList(id,null,null);
        }

        ReserveInfoVo reserveInfoVo = new ReserveInfoVo();
        BeanUtils.copyProperties(reserveDay, reserveInfoVo);
        Integer type = null;

        List<ReserveInfoItemVo> morning = new ArrayList<>();
        List<ReserveInfoItemVo> noon = new ArrayList<>();
        List<ReserveInfoItemVo> night = new ArrayList<>();
        List<ReserveInfoItemVo> midnight = new ArrayList<>();

        for (ReserveInfoItemVo reserveInfoItemVo : byReserveDayIdList) {

            type = reserveInfoItemVo.getType();

            if (type.equals(ReserveTimeTypeEnum.MORNING.getCode())) {
                morning.add(reserveInfoItemVo);
            }

            if (type.equals(ReserveTimeTypeEnum.NOON.getCode())) {
                noon.add(reserveInfoItemVo);
            }

            if (type.equals(ReserveTimeTypeEnum.NIGHT.getCode())) {
                night.add(reserveInfoItemVo);
            }
            if (type.equals(ReserveTimeTypeEnum.MIDNIGHT.getCode())) {
                midnight.add(reserveInfoItemVo);
            }

        }

        reserveInfoVo.setMorning(morning);
        reserveInfoVo.setNoon(noon);
        reserveInfoVo.setNight(night);
        reserveInfoVo.setMidnight(midnight);
        reserveInfoVo.setRemark(this.getByIdRemarks(id));
        return  reserveInfoVo;
    }

    @Override
    public List<ReserveDayRemark> getByIdRemarks(Integer reserveDayId) {
        QueryWrapper<ReserveDayRemark> reserveDayRemarkQueryWrapper = new QueryWrapper<>();
        reserveDayRemarkQueryWrapper.eq("reserve_day_id",reserveDayId);
        List<ReserveDayRemark> reserveDayRemarks = reserveDayRemarkMapper.selectList(reserveDayRemarkQueryWrapper);
        return reserveDayRemarks;
    }
}
