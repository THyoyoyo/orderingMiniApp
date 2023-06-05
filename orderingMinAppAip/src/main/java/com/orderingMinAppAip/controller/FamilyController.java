package com.orderingMinAppAip.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.orderingMinAppAip.annotation.Token;
import com.orderingMinAppAip.dto.family.FamilyDto;
import com.orderingMinAppAip.dto.family.ShiftMenberDto;
import com.orderingMinAppAip.dto.family.UpFamilyUserRemarkDto;
import com.orderingMinAppAip.enums.FamilyMemberIdentityTypeEnum;
import com.orderingMinAppAip.mapper.family.FamilyMapper;
import com.orderingMinAppAip.mapper.family.FamilyMemberMapper;
import com.orderingMinAppAip.model.family.Family;
import com.orderingMinAppAip.model.family.FamilyMember;
import com.orderingMinAppAip.service.FamilyService;
import com.orderingMinAppAip.util.CommTool;
import com.orderingMinAppAip.util.CurrentUserUtil;
import com.orderingMinAppAip.vo.common.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/family")
@Api(tags = "家庭组模块")
public class FamilyController {

    @Autowired
    FamilyService familyService;

    @Autowired
    FamilyMapper familyMapper;

    @Autowired
    FamilyMemberMapper familyMemberMapper;

    @PostMapping("/sava")
    @ApiOperation(value = "创建编辑家庭组信息")
    @Token
    public R familySava(@RequestBody FamilyDto dto) throws Exception {
        Integer userId = CurrentUserUtil.getUserId();

        //查询家庭加入信息
        FamilyMember familyMember = familyService.JoinNot(userId);

        Family family;

        if (dto.getId() != null) {
            if (!familyMember.getIdentityType().equals(FamilyMemberIdentityTypeEnum.ADMIN.getCode())) {
                return R.failed(404, "请联系户主，修改家庭信息");
            }
            family = familyService.getFamilyById(dto.getId());
            BeanUtils.copyProperties(dto, family);
            familyMapper.updateById(family);
        } else {
            //判断是否已创建或加入家庭
            if (familyMember != null) {
                return R.failed(404, "你已经有一个家庭了！请深情！");
            }
            family = new Family();
            BeanUtils.copyProperties(dto, family);
            family.setCreatorTime(new Date());
            family.setCreatorUserId(userId);
            family.setInvitationCode(CommTool.RandomNumberGenerator());
            familyMapper.insert(family);

            //加入创建的家庭组
            familyService.joinFamily(family.getId(), userId, FamilyMemberIdentityTypeEnum.ADMIN.getCode(), null);
        }

        return R.succeed();
    }

    @GetMapping("/join")
    @ApiOperation(value = "加入一个家庭组")
    @Token
    public R joinFamily(@RequestParam("family") Integer familyId, @RequestParam("invitationCode") Integer invitationCode,
                        @RequestParam(value = "visitTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date visitTime) throws Exception {
        Integer userId = CurrentUserUtil.getUserId();
        //查询的家庭信息
        Family familyInfo = familyService.getFamilyById(familyId);

        //判读邀请码类型
        Integer codeType = null;
        if (familyInfo.getInvitationCode().equals(invitationCode)) {
            codeType = FamilyMemberIdentityTypeEnum.MEMBER.getCode();
        }

        if (familyInfo.getGuestInvitationCode().equals(invitationCode)) {
            codeType = FamilyMemberIdentityTypeEnum.VISITOR.getCode();
        }

        if (codeType == null) {
            return R.failed(405, "邀请码错误");
        }


        //（成员）判读是否已经加入了一个家庭
        if (codeType.equals(FamilyMemberIdentityTypeEnum.MEMBER.getCode())) {
            FamilyMember familyMember = familyService.JoinNot(userId);
            if (familyMember != null) {
                return R.failed(405, "已加入了一个家庭，请深情！");
            }
        }
        //（访客是否填写日期）
        if (codeType.equals(FamilyMemberIdentityTypeEnum.VISITOR.getCode())) {
            if (visitTime == null) {
                return R.failed(406, "请设置访问时间");
            }
        }

        //（访客）判读是否已经加入了当前家庭
        FamilyMember familyMember = familyService.JoinNotVisitor(familyInfo.getId(), userId);
        if (familyMember != null) {
            String typeName = FamilyMemberIdentityTypeEnum.getNameById(familyMember.getIdentityType());
            return R.failed(405, "你以" + typeName + "身份已加入，请不要重复加入");
        }

        familyService.joinFamily(familyInfo.getId(), userId, codeType, visitTime);

        return R.succeed();
    }


    @GetMapping("/getFamilyInfo")
    @ApiOperation("获取我的家庭的信息（家庭、家庭成员、我的访问）")
    @Token
    public R getFamilyInfo() throws Exception {
        Integer userId = CurrentUserUtil.getUserId();
        FamilyMember familyMember = familyService.JoinNot(userId);
        Map<String, Object> familyByIdInfo = null;
        List<Map<String, Object>> familymember = null;
        if (familyMember != null) {
            familyByIdInfo = familyMapper.getFamilyByIdInfo(familyMember.getFamilyId());
            familymember = familyMemberMapper.getFamilymember(familyMember.getFamilyId());
        }

        List<Map<String, Object>> guestFamilys = familyService.getGuestFamilys();

        HashMap<String, Object> map = new HashMap<>();
        map.put("familyInfo", familyByIdInfo);
        map.put("familymember", familymember);
        map.put("guestFamilys", guestFamilys);
        return R.succeed(map);
    }


    @GetMapping("/getFamilyInfById")
    @ApiOperation("根据ID获取家庭信息（必须加入当前家庭才可查询）")
    @Token
    public R getFamilyInfById(@RequestParam("family") Integer familyId) throws Exception {

        Integer userId = CurrentUserUtil.getUserId();

        FamilyMember familyMember = familyService.JoinNotVisitor(familyId, userId);

        if (familyMember == null) {
            return R.failed(405, "未查询到相关家庭信息");
        }

        Map<String, Object> familyByIdInfo = familyMapper.getFamilyByIdInfo(familyId);

        return R.succeed(familyByIdInfo);
    }


    @PostMapping("/upFamilyUserRemark")
    @ApiOperation("更新家庭成员备注（必须加入当前家庭才可查询）")
    @Token
    public R upFamilyUserRemark(UpFamilyUserRemarkDto dto) throws Exception {
        Integer userId = CurrentUserUtil.getUserId();
        FamilyMember atFamilyMember = familyService.JoinNotVisitor(dto.getFamilyId(), userId);

        if (atFamilyMember == null) {
            return R.failed(405, "不存在于该家庭组！");
        }

        if (atFamilyMember.getIdentityType().equals(FamilyMemberIdentityTypeEnum.VISITOR.getCode())) {
            return R.failed(405, "访客不可修改，家庭成员备注！");
        }

        FamilyMember familyMember = familyMemberMapper.selectById(dto.getId());
        if (familyMember == null) {
            return R.failed(405, "未查询到家庭信息！");
        }
        familyMember.setNameRemark(dto.getUserRemark());
        familyMemberMapper.updateById(familyMember);

        return R.succeed();
    }


    @PostMapping("/shiftMenber")
    @ApiOperation("移出成员")
    @Token
    public R shiftMenber(ShiftMenberDto dto) throws Exception {
        Integer userId = CurrentUserUtil.getUserId();
        FamilyMember atFamilyMember = familyService.JoinNotVisitor(dto.getFamilyId(), userId);

        if (atFamilyMember == null) {
            return R.failed(405, "不存在于该家庭组！");
        }

        if (!atFamilyMember.getIdentityType().equals(FamilyMemberIdentityTypeEnum.ADMIN.getCode())) {
            return R.failed(405, "你不能移出该成员，请联系户主");
        }

        familyMemberMapper.deleteById(dto.getId());
        return R.succeed();
    }


    @GetMapping("/leaveMenber")
    @ApiOperation("离开家庭组（取消访问）")
    @Token
    public R leaveMenber(@RequestParam("familyId") Integer familyId) throws Exception{

        Integer userId = CurrentUserUtil.getUserId();
        FamilyMember atFamilyMember = familyService.JoinNotVisitor(familyId, userId);

        if (atFamilyMember == null) {
            return R.failed(405, "不存在于该家庭组！");
        }

        if(atFamilyMember.getIdentityType().equals(FamilyMemberIdentityTypeEnum.ADMIN.getCode())){
            List<Map<String, Object>> familymember = familyMemberMapper.getFamilymember(familyId);
            if(familymember.size() > 0){
                return  R.failed(405,"请清理剩余成员后再退出家庭组");
            }
            familyMapper.deleteById(atFamilyMember.getFamilyId());
        }
        familyMemberMapper.deleteById(atFamilyMember.getId());
        return R.succeed();
    }
}
