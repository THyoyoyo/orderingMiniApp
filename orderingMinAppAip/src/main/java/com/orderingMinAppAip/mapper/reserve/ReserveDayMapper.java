package com.orderingMinAppAip.mapper.reserve;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.orderingMinAppAip.model.reserve.ReserveDay;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface ReserveDayMapper extends BaseMapper<ReserveDay> {

    List<Map<String,Object>> getReserveDateAllByMonth(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}