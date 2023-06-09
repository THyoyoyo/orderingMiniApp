package com.orderingMinAppAip.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orderingMinAppAip.annotation.Token;
import com.orderingMinAppAip.dto.dishes.GetClassListVDto;
import com.orderingMinAppAip.dto.dishes.SavaClassDto;
import com.orderingMinAppAip.dto.dishes.SavaDishesDto;
import com.orderingMinAppAip.enums.CommTypeEnum;
import com.orderingMinAppAip.enums.FamilyMemberIdentityTypeEnum;
import com.orderingMinAppAip.mapper.dishes.*;
import com.orderingMinAppAip.model.dishes.*;
import com.orderingMinAppAip.model.family.FamilyMember;
import com.orderingMinAppAip.service.DishesService;
import com.orderingMinAppAip.service.FamilyService;
import com.orderingMinAppAip.util.CurrentUserUtil;
import com.orderingMinAppAip.vo.common.PageVo;
import com.orderingMinAppAip.vo.common.R;
import com.orderingMinAppAip.vo.dishes.DishesSearchVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/dishes")
@Api(tags = "菜品模块")
public class DishesController {

    @Autowired
    FamilyService familyService;

    @Autowired
    DishesClassMapper dishesClassMapper;

    @Autowired
    DishesMapper dishesMapper;

    @Autowired
    DishesService dishesService;

    @Autowired
    DishesImgMapper dishesImgMapper;

    @Autowired
    DishesSonInfoMapper dishesSonInfoMapper;

    @Autowired
    DishesSonMapper dishesSonMapper;

    @Autowired
    DishesInfoMapper dishesInfoMapper;

    @PostMapping("/class/sava")
    @ApiOperation("新增编辑菜品分类")
    @Token
    public R savaClass(@RequestBody SavaClassDto dto) throws Exception {
        FamilyMember joinFamily = familyService.isJoinFamily(dto.getFamilyId());
        if (joinFamily == null || joinFamily.getIdentityType().equals(FamilyMemberIdentityTypeEnum.VISITOR.getCode())) {
            return R.failed(405, "不可添加分类，请联系家庭成员");
        }

        DishesClass dishesClass = new DishesClass();

        if (dto.getId() != null) {
            dishesClass = dishesClassMapper.selectById(dto.getId());
            dishesClass.setName(dto.getName());
            dishesClassMapper.updateById(dishesClass);
        } else {
            BeanUtils.copyProperties(dto, dishesClass);
            dishesClass.setCreatorTime(new Date());
            dishesClass.setCreatorUserId(CurrentUserUtil.getUserId());
            dishesClass.setCreatorName(CurrentUserUtil.getUserName());
            dishesClassMapper.insert(dishesClass);
        }

        return R.succeed(dishesClass);
    }

    @GetMapping("/class/get")
    @ApiOperation("获取菜品分类")
    @Token
    public R getClass(@RequestParam("familyId") Integer familyId) throws Exception {

        FamilyMember joinFamily = familyService.isJoinFamily(familyId);
        if (joinFamily == null) {
            return R.failed(405, "你不属于该家庭！");
        }

        QueryWrapper<DishesClass> dishesClassQueryWrapper = new QueryWrapper<>();

        //访客不可查看禁用分类
        if (joinFamily.getIdentityType().equals(FamilyMemberIdentityTypeEnum.VISITOR.getCode())) {
            dishesClassQueryWrapper.eq("family_id", familyId).eq("status", CommTypeEnum.start.getCode());
        } else {
            dishesClassQueryWrapper.eq("family_id", familyId);
        }


        List<DishesClass> dishesClasses = dishesClassMapper.selectList(dishesClassQueryWrapper);
        return R.succeed(dishesClasses);
    }

    @GetMapping("/class/del")
    @ApiOperation("删除分类")
    @Token
    public R delClass(@RequestParam("id") Integer id, @RequestParam("familyId") Integer familyId) throws Exception {
        FamilyMember joinFamily = familyService.isJoinFamily(familyId);
        if (joinFamily == null) {
            return R.failed(405, "你不属于该家庭！");
        }
        dishesClassMapper.deleteById(id);
        return R.succeed();
    }


    @PostMapping("/search")
    @ApiOperation("获取分类下的菜品")
    @Token
    public R getClassList(@RequestBody GetClassListVDto dto) throws Exception {
        // 分类归属查询
        FamilyMember joinFamily = familyService.isJoinFamily(dto.getFamilyId());
        if (joinFamily == null || joinFamily.getIdentityType().equals(FamilyMemberIdentityTypeEnum.VISITOR.getCode())) {
            return R.failed(405, "请查询正确的分类信息，你不属于该家庭！");
        }
        Page<DishesSearchVo> pageInfo = new Page<>(dto.getPageNum(), dto.getPageSize());
        HashMap<String, Object> map = new HashMap<>();
        map.put("dishesClassId",dto.getDishesClassId());
        map.put("familyId",dto.getFamilyId());
        dishesMapper.getList(pageInfo,map);
        PageVo<DishesSearchVo> PageVo = new PageVo<>();
        PageVo.setList(pageInfo.getRecords());
        PageVo.setPages(pageInfo.getPages());
        PageVo.setTotal(pageInfo.getTotal());
        PageVo.setPageSize(pageInfo.getSize());
        PageVo.setPageNum(pageInfo.getCurrent());
        return R.succeed(PageVo);
    }


    @PostMapping("/sava")
    @ApiOperation("新增编辑菜品")
    @Token
    public R sava(@RequestBody SavaDishesDto savaDishesDto) throws Exception {
        FamilyMember joinFamily = familyService.isJoinFamily(savaDishesDto.getFamilyId());
        if (joinFamily == null || joinFamily.getIdentityType().equals(FamilyMemberIdentityTypeEnum.VISITOR.getCode())) {
            return R.failed(405, "不可添加&编辑菜品，请联系家庭成员");
        }
        Dishes dishes = null;
        if (savaDishesDto.getId() !=null){
            dishes = dishesMapper.selectById(savaDishesDto.getId());
            BeanUtils.copyProperties(savaDishesDto,dishes);
            dishesMapper.updateById(dishes);
        }else {
            dishes = new Dishes();
            BeanUtils.copyProperties(savaDishesDto,dishes);
            dishes.setCreatorName(CurrentUserUtil.getUserName());
            dishes.setCreatorUserId(CurrentUserUtil.getUserId());
            dishes.setCreatorTime(new Date());
            dishesMapper.insert(dishes);
        }

        dishesService.upDisheClass(savaDishesDto,dishes);
        dishesService.upDishesImg(savaDishesDto,dishes);
        dishesService.upDisheSon(savaDishesDto,dishes);
        return  R.succeed();
    }


    @GetMapping("/info")
    @ApiOperation("根据ID获取菜品详情")
    @Token
    public R getInfo(@RequestParam("id") Integer id){
        Dishes dishes = dishesMapper.selectById(id);
        SavaDishesDto savaDishesDto = new SavaDishesDto();
        BeanUtils.copyProperties(dishes, savaDishesDto);

        QueryWrapper<DishesImg> dishesImgQueryWrapper = new QueryWrapper<>();
        dishesImgQueryWrapper.eq("dishes_id",id);
        List<DishesImg> dishesImgs = dishesImgMapper.selectList(dishesImgQueryWrapper);
        List<String> newImgList = new ArrayList<>();
        for (DishesImg dishesImg : dishesImgs) {
            newImgList.add(dishesImg.getImg());
        }
        savaDishesDto.setImgList(newImgList);

        List<Map<String, Object>> byDishesIdList = dishesSonInfoMapper.getByDishesIdList(id);

        List<String> newDisheSonList = new ArrayList<>();
        for (Map<String, Object> stringObjectMap : byDishesIdList) {
            String dishesSonName = stringObjectMap.get("dishesSonName").toString();
            newDisheSonList.add(dishesSonName);
        }

        savaDishesDto.setDishesSonList(newDisheSonList);

        return R.succeed(savaDishesDto);
    }


    @GetMapping("/del")
    @ApiOperation("/根据ID删除菜品")
    @Token
    public R delById(@RequestParam("familyId") Integer familyId, @RequestParam("id") Integer id) throws Exception {

        // 分类归属查询
        FamilyMember joinFamily = familyService.isJoinFamily(familyId);
        if (joinFamily == null || joinFamily.getIdentityType().equals(FamilyMemberIdentityTypeEnum.VISITOR.getCode())) {
            return R.failed(405, "你不属于该家庭！,能删除别人的菜品！");
        }

        dishesMapper.deleteById(id);

        QueryWrapper<DishesInfo> dishesInfoQueryWrapper = new QueryWrapper<>();
        dishesInfoQueryWrapper.eq("dishesId",id);
        dishesInfoMapper.delete(dishesInfoQueryWrapper);
        return  R.succeed();
    }
}
