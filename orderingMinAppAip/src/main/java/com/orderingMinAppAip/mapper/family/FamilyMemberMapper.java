package com.orderingMinAppAip.mapper.family;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.orderingMinAppAip.model.family.FamilyMember;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface FamilyMemberMapper extends BaseMapper<FamilyMember> {


   List<Map<String,Object>> getFamilys(@Param("userId") Integer userId);

}