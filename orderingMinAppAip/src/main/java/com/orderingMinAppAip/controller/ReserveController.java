package com.orderingMinAppAip.controller;

import com.orderingMinAppAip.annotation.Token;
import com.orderingMinAppAip.dto.reserve.ReserveSavaDto;
import com.orderingMinAppAip.enums.ReserveTimeTypeEnum;
import com.orderingMinAppAip.mapper.reserve.ReserveDayInfoMapper;
import com.orderingMinAppAip.mapper.reserve.ReserveDayMapper;
import com.orderingMinAppAip.model.family.FamilyMember;
import com.orderingMinAppAip.model.reserve.ReserveDay;
import com.orderingMinAppAip.model.reserve.ReserveDayInfo;
import com.orderingMinAppAip.service.FamilyService;
import com.orderingMinAppAip.service.ReserveService;
import com.orderingMinAppAip.util.CurrentUserUtil;
import com.orderingMinAppAip.vo.common.R;
import com.orderingMinAppAip.vo.reserve.ReserveInfoItemVo;
import com.orderingMinAppAip.vo.reserve.ReserveInfoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/reserve")
@Api(tags = "预约模块")
public class ReserveController {

    @Autowired
    FamilyService familyService;

    @Autowired
    ReserveService reserveService;

    @Autowired
    ReserveDayMapper reserveDayMapper;

    @Autowired
    ReserveDayInfoMapper reserveDayInfoMapper;

    @PostMapping("/sava")
    @ApiOperation("新增编辑预约")
    @Token
    public R reserveSava(@RequestBody ReserveSavaDto dto) throws Exception {
        FamilyMember joinFamily = familyService.isJoinFamily(dto.getFamilyId());
        if (joinFamily == null) {
            return R.failed(405, "你没有加入该家庭，不能进行预约！");
        }
        Integer userId = CurrentUserUtil.getUserId();
        String userName = CurrentUserUtil.getUserName();

        ReserveDay reserveDay = null;
        List<ReserveDayInfo> oldReserveDayInfos = null;

        if (dto.getId() != null) {
            reserveDay = reserveDayMapper.selectById(dto.getId());
            reserveDay.setCreator(dto.getCreator());
            reserveDayMapper.updateById(reserveDay);
            oldReserveDayInfos = reserveService.delByIdInfo(dto.getId());
        } else {
            reserveDay = new ReserveDay();
            reserveDay.setCreator(dto.getCreator());
            reserveDay.setFamilyId(dto.getFamilyId());
            reserveDay.setStatus(1);
            reserveDay.setCreatorTime(new Date());
            reserveDay.setCreatorUserId(userId);
            reserveDay.setCreatorUser(userName);
            reserveDayMapper.insert(reserveDay);
        }


        for (Integer dishesId : dto.getMorning()) {
            ReserveDayInfo reserveDishes = isReserveDishes(oldReserveDayInfos, dishesId, ReserveTimeTypeEnum.MORNING.getCode());
            reserveService.addDishesInfo(dishesId, ReserveTimeTypeEnum.MORNING.getCode(), userId, userName, reserveDay.getId(), reserveDishes);
        }

        for (Integer dishesId : dto.getNoon()) {
            ReserveDayInfo reserveDishes = isReserveDishes(oldReserveDayInfos, dishesId, ReserveTimeTypeEnum.NOON.getCode());
            reserveService.addDishesInfo(dishesId, ReserveTimeTypeEnum.NOON.getCode(), userId, userName, reserveDay.getId(), reserveDishes);
        }

        for (Integer dishesId : dto.getNight()) {
            ReserveDayInfo reserveDishes = isReserveDishes(oldReserveDayInfos, dishesId, ReserveTimeTypeEnum.NIGHT.getCode());
            reserveService.addDishesInfo(dishesId, ReserveTimeTypeEnum.NIGHT.getCode(), userId, userName, reserveDay.getId(), reserveDishes);
        }

        for (Integer dishesId : dto.getMidnight()) {
            ReserveDayInfo reserveDishes = isReserveDishes(oldReserveDayInfos, dishesId, ReserveTimeTypeEnum.MIDNIGHT.getCode());
            reserveService.addDishesInfo(dishesId, ReserveTimeTypeEnum.MIDNIGHT.getCode(), userId, userName, reserveDay.getId(), reserveDishes);
        }
        return R.succeed();
    }

    public ReserveDayInfo isReserveDishes(List<ReserveDayInfo> oldReserveDayInfos, Integer dishesId, Integer type) {
        ReserveDayInfo reserveDayInfo = null;
        if (oldReserveDayInfos != null) {
            Optional<ReserveDayInfo> reserveDayInfoOptional = oldReserveDayInfos.stream()
                    .filter(info -> info.getDishesId().equals(dishesId) && info.getType().equals(type))
                    .findFirst();
            if (reserveDayInfoOptional.isPresent()) {
                reserveDayInfo = reserveDayInfoOptional.get();
            }
        }
        return reserveDayInfo;
    }


    @GetMapping("/info")
    @ApiOperation("根据ID获取预约详情")
    @Token
    public R getInfoById(@RequestParam("familyId") Integer familyId, @RequestParam("id") Integer id) throws Exception {
        FamilyMember joinFamily = familyService.isJoinFamily(familyId);
        if (joinFamily == null) {
            return R.failed(405, "没有加入该家庭,无法查看当前预约");
        }
        ReserveDay reserveDay = reserveDayMapper.selectById(id);

        List<ReserveInfoItemVo> byReserveDayIdList = reserveDayInfoMapper.getByReserveDayIdList(id);

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

        return R.succeed(reserveInfoVo);
    }


}
