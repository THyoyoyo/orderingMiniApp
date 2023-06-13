package com.orderingMinAppAip.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orderingMinAppAip.annotation.Token;
import com.orderingMinAppAip.dto.reserve.AddRemarkDto;
import com.orderingMinAppAip.dto.reserve.ReserveListDto;
import com.orderingMinAppAip.dto.reserve.ReserveSavaDto;
import com.orderingMinAppAip.enums.ReserveStatusEnum;
import com.orderingMinAppAip.enums.ReserveTimeTypeEnum;
import com.orderingMinAppAip.mapper.reserve.ReserveDayInfoMapper;
import com.orderingMinAppAip.mapper.reserve.ReserveDayMapper;
import com.orderingMinAppAip.mapper.reserve.ReserveDayRemarkMapper;
import com.orderingMinAppAip.model.family.FamilyMember;
import com.orderingMinAppAip.model.reserve.ReserveDay;
import com.orderingMinAppAip.model.reserve.ReserveDayInfo;
import com.orderingMinAppAip.model.reserve.ReserveDayRemark;
import com.orderingMinAppAip.service.FamilyService;
import com.orderingMinAppAip.service.ReserveService;
import com.orderingMinAppAip.util.CurrentUserUtil;
import com.orderingMinAppAip.vo.common.PageVo;
import com.orderingMinAppAip.vo.common.R;
import com.orderingMinAppAip.vo.dishes.DishesSearchVo;
import com.orderingMinAppAip.vo.reserve.ReserveInfoItemVo;
import com.orderingMinAppAip.vo.reserve.ReserveInfoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
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

    @Autowired
    ReserveDayRemarkMapper reserveDayRemarkMapper;

    @PostMapping("/sava")
    @ApiOperation("新增编辑预约")
    @Token
    public R reserveSava(@RequestBody ReserveSavaDto dto) throws Exception {
        FamilyMember joinFamily = familyService.isJoinFamily(dto.getFamilyId());
        if (joinFamily == null) {
            return R.failed(405, "你没有加入该家庭，不能进行预约！");
        }
        String formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dto.getCreator());
        Integer userId = CurrentUserUtil.getUserId();
        String userName = CurrentUserUtil.getUserName();
        ReserveDay reserveDay = null;
        List<ReserveDayInfo> oldReserveDayInfos = null;
        Integer dayReserver = reserveService.isDayReserver(dto.getCreator(),dto.getFamilyId());

        if (dto.getId() != null) {
            reserveDay = reserveDayMapper.selectById(dto.getId());
            if(!reserveDay.getCreator().equals(dto.getCreator()) && dayReserver > 0){
                    return  R.failed(405,formatDate +" 已预约");
            }
            reserveDay.setCreator(dto.getCreator());
            reserveDayMapper.updateById(reserveDay);
            oldReserveDayInfos = reserveService.delByIdInfo(dto.getId());
        } else {
            if(dayReserver > 0){
                return  R.failed(405,formatDate +" 已预约");
            }
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
        ReserveInfoVo byIdInfo = reserveService.getByIdInfo(familyId, id,joinFamily);
        return R.succeed(byIdInfo);
    }


    @PostMapping("/list")
    @ApiOperation("预约列表")
    @Token
    public R getList(@RequestBody ReserveListDto dto) throws Exception {
        FamilyMember joinFamily = familyService.isJoinFamily(dto.getFamilyId());
        if (joinFamily == null) {
            return R.failed(405, "没有加入该家庭,无法查看当前预约");
        }


        QueryWrapper<ReserveDay> reserveDayQueryWrapper = new QueryWrapper<>();
        reserveDayQueryWrapper.eq("family_id",dto.getFamilyId());
        reserveDayQueryWrapper.orderByDesc("creator_time");

        Page<ReserveDay> reserveDayPage = reserveDayMapper.selectPage(new Page<>(dto.getPageNum(), dto.getPageSize()), reserveDayQueryWrapper);

        List<ReserveInfoVo> reserveInfoVos = new ArrayList<>();

        for (ReserveDay record :  reserveDayPage.getRecords()) {
            ReserveInfoVo byIdInfo = reserveService.getByIdInfo(record.getFamilyId(),record.getId(),null);
            reserveInfoVos.add(byIdInfo);
        }

        PageVo<ReserveInfoVo> PageVo = new PageVo<>();
        PageVo.setPages(reserveDayPage.getPages());
        PageVo.setTotal(reserveDayPage.getTotal());
        PageVo.setList(reserveInfoVos);
        PageVo.setPageSize(reserveDayPage.getSize());
        PageVo.setPageNum(reserveDayPage.getCurrent());

        return R.succeed(PageVo);
    }

    @GetMapping("/cancel")
    @ApiOperation("取消预约")
    @Token
    public R cancelById(@RequestParam("familyId") Integer familyId, @RequestParam("id") Integer id) throws Exception {
        FamilyMember joinFamily = familyService.isJoinFamily(familyId);
        if (joinFamily == null) {
            return R.failed(405, "操作权限不够！");
        }

        ReserveDay reserveDay = reserveDayMapper.selectById(id);
        reserveDay.setStatus(ReserveStatusEnum.CANCEL.getCode());
        reserveDayMapper.updateById(reserveDay);
        return R.succeed();
    }

    @PostMapping("/addRemark")
    @ApiOperation("新增一条预约备注")
    @Token
    public R addRemark(@RequestBody AddRemarkDto dto) throws Exception {
        ReserveDayRemark reserveDayRemark = new ReserveDayRemark();
        reserveDayRemark.setReserveDayId(dto.getReserveDayId());
        reserveDayRemark.setContent(dto.getContent());
        if(dto.getParentId()!=null){
            reserveDayRemark.setParentId(dto.getParentId());
        }
        reserveDayRemark.setCreatorUser(CurrentUserUtil.getUserName());
        reserveDayRemark.setCreatorUserId(CurrentUserUtil.getUserId());
        reserveDayRemark.setCreatorTime(new Date());
        reserveDayRemarkMapper.insert(reserveDayRemark);
        return R.succeed(reserveDayRemark);
    }

    @GetMapping("/isDayReserver")
    @ApiOperation("测试")
    @Token
  public R isDayReserver(@RequestParam("id") Integer id  ,
                         @RequestParam(value = "date", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date date
                         ){
        Integer dayReserver = reserveService.isDayReserver(date, id);
        String formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        if(dayReserver > 0){
            return  R.failed(405,formatDate +" 已预约");
        }
        return R.succeed(dayReserver);
    }
}
