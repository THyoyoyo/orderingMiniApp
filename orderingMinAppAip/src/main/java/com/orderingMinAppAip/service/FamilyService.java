package com.orderingMinAppAip.service;

import com.orderingMinAppAip.model.family.Family;
import com.orderingMinAppAip.model.family.FamilyMember;

import java.util.Date;
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
    void joinFamily(Integer familyId, Integer memberId, Integer identityType, Date visitTime);

    /**
     * 获取传入一个用户 家庭信息
     * */
    FamilyMember  isJoinFamily(Integer familyId, Integer memberId);

    /**
     * 获取当前用户家庭信息
     * */
    FamilyMember isJoinFamily(Integer familyId) throws Exception;

    /**
     * 成员是否已加入一个家庭
     * */
    FamilyMember JoinNot(Integer userId);


    /**
     * 判读是否存在于家庭组中 包含访客
     * */
    FamilyMember JoinNotVisitor(Integer familyId,Integer userId);


    /**
     *  访客获取已加入的家庭
     * */
    List<Map<String, Object>> getGuestFamilys() throws Exception;

    List<FamilyMember> getFamilys(Integer userId);
}
