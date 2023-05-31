package com.orderingMinAppAip.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.orderingMinAppAip.annotation.Token;
import com.orderingMinAppAip.dto.family.FamilyDto;
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
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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
            familyService.joinFamily(family.getId(), userId, FamilyMemberIdentityTypeEnum.ADMIN.getCode(),family.getInvitationCode());
        }

        return R.succeed();
    }

    @GetMapping("/join")
    @ApiOperation(value = "加入一个家庭组")
    @Token
    public R joinFamily(@RequestParam("family") Integer familyId, @RequestParam("invitationCode") Integer invitationCode) throws Exception {
        Integer userId = CurrentUserUtil.getUserId();

        //查询家庭加入信息
        FamilyMember familyMember = familyService.JoinNot(userId);

        if (familyMember!=null && familyId.equals(familyMember.getFamilyId())) {
            return R.failed(404, "你已在这个家庭里面了！");
        }

        if (familyMember != null) {
            return R.failed(404, "你已经有一个家庭了！请深情！");
        }

        Family familyInfo = familyService.getFamilyById(familyId);

        if (familyInfo == null) {
            return R.failed(404, "为找到该家庭组");
        }

        //加入创建的家庭组
        Boolean isJoin = familyService.joinFamily(familyId, userId, FamilyMemberIdentityTypeEnum.MEMBER.getCode(), invitationCode);

        if(!isJoin){
            return R.failed(404,"邀请码错误");
        }

        return R.succeed();
    }

    @GetMapping("/visitorJoin")
    @ApiOperation(value = "访客加入家庭")
    @Token
    public R visitorJoin(@RequestParam("family") Integer familyId, @RequestParam("invitationCode") Integer invitationCode) throws Exception {
        Integer userId = CurrentUserUtil.getUserId();

        Family familyInfo = familyService.getFamilyById(familyId);

        if (familyInfo == null) {
            return R.failed(404, "没有找到该家庭组");
        }

        //查询家庭加入信息
        FamilyMember familyMember = familyService.JoinNotVisitor(familyId,userId);

        if (familyMember!=null) {
            return R.failed(404, "你已在这个家庭里面了！");
        }

        //加入创建的家庭组
        Boolean isJoin = familyService.joinFamily(familyId, userId, FamilyMemberIdentityTypeEnum.VISITOR.getCode(), invitationCode);

        if(!isJoin){
            return R.failed(404,"邀请码错误");
        }

        return R.succeed();
    }


    @GetMapping("/getFamilyInfo")
    @ApiOperation("获取当前家庭的信息 主：唯一：访客多个")
    @Token
    public R getFamilyInfo() throws Exception {
        List<Map<String, Object>> familys = familyService.getFamilys();
        return  R.succeed(familys);
    }

}
