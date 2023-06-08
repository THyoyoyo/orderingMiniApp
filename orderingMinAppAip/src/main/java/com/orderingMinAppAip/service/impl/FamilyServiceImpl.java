package com.orderingMinAppAip.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.orderingMinAppAip.enums.FamilyMemberIdentityTypeEnum;
import com.orderingMinAppAip.mapper.family.FamilyMapper;
import com.orderingMinAppAip.mapper.family.FamilyMemberMapper;
import com.orderingMinAppAip.model.family.Family;
import com.orderingMinAppAip.model.family.FamilyMember;
import com.orderingMinAppAip.service.FamilyService;
import com.orderingMinAppAip.util.CurrentUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
public class FamilyServiceImpl implements FamilyService {

    @Autowired
    FamilyMapper familyMapper;

    @Autowired
    FamilyMemberMapper familyMemberMapper;

    @Override
    public Family getFamilyById(Integer id) {
        Family family = familyMapper.selectById(id);
        return family;
    }

    @Override
    public void joinFamily(Integer familyId, Integer memberId, Integer identityType,Date visitTime) {


        FamilyMember familyMember = new FamilyMember();
        familyMember.setFamilyId(familyId);
        familyMember.setMemberId(memberId);
        familyMember.setIdentityType(identityType);
        familyMember.setCreatorTime(new Date());
        if(identityType.equals(FamilyMemberIdentityTypeEnum.VISITOR.getCode())){
            familyMember.setVisitTime(visitTime);
        }
        familyMemberMapper.insert(familyMember);
    }

    @Override
    public FamilyMember isJoinFamily(Integer familyId, Integer memberId) {
        QueryWrapper<FamilyMember> familyMemberMapperQueryWrapper = new QueryWrapper<>();
        familyMemberMapperQueryWrapper.eq("family_id",familyId).eq("member_id", memberId).last("limit 0,1");
        FamilyMember familyMember = familyMemberMapper.selectOne(familyMemberMapperQueryWrapper);
        return familyMember;

    }
    @Override
    public FamilyMember isJoinFamily(Integer familyId) throws Exception {
        Integer userId = CurrentUserUtil.getUserId();
        QueryWrapper<FamilyMember> familyMemberMapperQueryWrapper = new QueryWrapper<>();
        familyMemberMapperQueryWrapper.eq("family_id",familyId).eq("member_id", userId).last("limit 0,1");
        FamilyMember familyMember = familyMemberMapper.selectOne(familyMemberMapperQueryWrapper);
        return familyMember;

    }

    @Override
    public FamilyMember JoinNot(Integer userId) {
        QueryWrapper<FamilyMember> familyMemberMapperQueryWrapper = new QueryWrapper<>();
        familyMemberMapperQueryWrapper.eq("member_id", userId).ne("identity_type",FamilyMemberIdentityTypeEnum.VISITOR.getCode()).last("limit 0,1");
        FamilyMember familyMember = familyMemberMapper.selectOne(familyMemberMapperQueryWrapper);

        return familyMember;
    }

    @Override
    public FamilyMember JoinNotVisitor(Integer familyId,Integer userId) {
        QueryWrapper<FamilyMember> familyMemberMapperQueryWrapper = new QueryWrapper<>();
        familyMemberMapperQueryWrapper.eq("member_id", userId).eq("family_id",familyId).last("limit 0,1");
        FamilyMember familyMember = familyMemberMapper.selectOne(familyMemberMapperQueryWrapper);
        return familyMember;
    }

    @Override
    public  List<Map<String, Object>> getGuestFamilys() throws Exception {
        Integer userId = CurrentUserUtil.getUserId();
        return familyMemberMapper.getGuestFamilys(userId);
    }

}
