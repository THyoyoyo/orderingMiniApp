package com.orderingMinAppAip.mapper.dishes;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.orderingMinAppAip.model.dishes.DishesSonInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


@Mapper
public interface DishesSonInfoMapper extends BaseMapper<DishesSonInfo> {
  List<Map<String,Object>> getByDishesIdList(@Param("dishesId") Integer dishesId);
}