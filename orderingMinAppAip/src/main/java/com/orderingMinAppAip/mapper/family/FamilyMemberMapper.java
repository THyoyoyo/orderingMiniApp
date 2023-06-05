package com.orderingMinAppAip.mapper.family;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.orderingMinAppAip.model.family.FamilyMember;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface FamilyMemberMapper extends BaseMapper<FamilyMember> {

   //获自己的访客列表
   List<Map<String,Object>> getGuestFamilys(@Param("userId") Integer userId);

   //获取家庭成员
   List<Map<String,Object>> getFamilymember(@Param("familyId") Integer familyId);

}