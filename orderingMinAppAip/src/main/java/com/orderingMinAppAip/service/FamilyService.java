package com.orderingMinAppAip.service;

import com.orderingMinAppAip.model.family.Family;
import com.orderingMinAppAip.model.family.FamilyMember;

import java.util.List;
import java.util.Map;

public interface FamilyService {

    /**
     * 根据ID获取一个家庭信息
     * */
    Family getFamilyById(Integer id);

    /**
     * 加入一个家庭
     * */
    Boolean joinFamily(Integer familyId, Integer memberId,Integer identityType,Integer invitationCode);


    /**
     * 是否已加入一个家庭
     * */
    FamilyMember JoinNot(Integer userId);


    /**
     * 判读访客是否已加入
     * */
    FamilyMember JoinNotVisitor(Integer familyId,Integer userId);


    /**
     *  获取已加入的家庭
     * */
    List<Map<String, Object>> getFamilys() throws Exception;
}
