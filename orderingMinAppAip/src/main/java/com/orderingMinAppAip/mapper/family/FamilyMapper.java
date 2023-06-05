package com.orderingMinAppAip.mapper.family;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.orderingMinAppAip.model.family.Family;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

@Mapper
public interface FamilyMapper extends BaseMapper<Family> {
    Map<String,Object> getFamilyByIdInfo(@Param("id") Integer id);
}