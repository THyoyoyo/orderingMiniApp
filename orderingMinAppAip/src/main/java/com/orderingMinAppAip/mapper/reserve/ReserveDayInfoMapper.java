package com.orderingMinAppAip.mapper.reserve;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.orderingMinAppAip.vo.reserve.ReserveInfoItemVo;
import org.apache.ibatis.annotations.Mapper;
import com.orderingMinAppAip.model.reserve.ReserveDayInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface ReserveDayInfoMapper extends BaseMapper<ReserveDayInfo> {
    List<ReserveInfoItemVo> getByReserveDayIdList(@Param("reserveDayId") Integer reserveDayId,@Param("identityType") Integer identityType,@Param("userId") Integer userId);
}