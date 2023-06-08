package com.orderingMinAppAip.mapper.dishes;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orderingMinAppAip.model.dishes.Dishes;
import com.orderingMinAppAip.vo.dishes.DishesSearchVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Mapper
public interface DishesMapper extends BaseMapper<Dishes> {

    IPage<DishesSearchVo> getList(Page<DishesSearchVo> pageInfo, @Param("params") Map<String, Object> map);
}