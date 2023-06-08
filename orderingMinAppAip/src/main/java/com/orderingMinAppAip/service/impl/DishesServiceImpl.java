package com.orderingMinAppAip.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.orderingMinAppAip.dto.dishes.SavaDishesDto;
import com.orderingMinAppAip.mapper.dishes.*;
import com.orderingMinAppAip.model.dishes.*;
import com.orderingMinAppAip.service.DishesService;
import com.orderingMinAppAip.util.CurrentUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class DishesServiceImpl implements DishesService {

    @Autowired
    DishesMapper dishesMapper;
    @Autowired
    DishesImgMapper dishesImgMapper;
    @Autowired
    DishesSonInfoMapper dishesSonInfoMapper;
    @Autowired
    DishesSonMapper dishesSonMapper;

    @Autowired
    DishesInfoMapper dishesInfoMapper;



    @Override
    public void upDishesImg(SavaDishesDto savaDishesDto, Dishes dishes) {
        QueryWrapper<DishesImg> dishesImgQueryWrapper = new QueryWrapper<>();
        dishesImgQueryWrapper.eq("dishes_id",dishes.getId());
        dishesImgMapper.delete(dishesImgQueryWrapper);
        for (String imgItem : savaDishesDto.getImgList()) {
            DishesImg dishesImg = new DishesImg();
            dishesImg.setDishesId(dishes.getId());
            dishesImg.setImg(imgItem);
            dishesImgMapper.insert(dishesImg);
        }
    }

    @Override
    public void upDisheSon(SavaDishesDto savaDishesDto, Dishes dishes) {
        QueryWrapper<DishesSonInfo> dishesSonInfoQueryWrapper = new QueryWrapper<>();
        dishesSonInfoQueryWrapper.eq("dishes_id",dishes.getId());
        dishesSonInfoMapper.delete(dishesSonInfoQueryWrapper);
        //更新菜品食材 &&  判断上传的食材是否存在于食材库中，如果不存在则添加进食材库
        for (String sonItem : savaDishesDto.getDishesSonList()) {
            QueryWrapper<DishesSon> dishesSonQueryWrapper = new QueryWrapper<>();
            dishesSonQueryWrapper.eq("family_id", savaDishesDto.getFamilyId()).eq("name",sonItem);
            DishesSon dishesSon = dishesSonMapper.selectOne(dishesSonQueryWrapper);
            if(dishesSon == null){
                dishesSon = new DishesSon();
                dishesSon.setFamilyId(dishes.getFamilyId());
                dishesSon.setName(sonItem);
                dishesSonMapper.insert(dishesSon);
            }
            DishesSonInfo dishesSonInfo = new DishesSonInfo();
            dishesSonInfo.setDishesId(dishes.getId());
            dishesSonInfo.setDishesSonId(dishesSon.getId());
            dishesSonInfoMapper.insert(dishesSonInfo);
        }
    }

    @Override
    public void upDisheClass(SavaDishesDto savaDishesDto, Dishes dishes) throws Exception {
        DishesInfo dishesInfo = null;
        if(savaDishesDto.getId()!=null){
            QueryWrapper<DishesInfo> dishesInfoQueryWrapper = new QueryWrapper<>();
            dishesInfoQueryWrapper.eq("dishes_id",savaDishesDto.getId());
              dishesInfo = dishesInfoMapper.selectOne(dishesInfoQueryWrapper);
              dishesInfo.setDishesClassId(savaDishesDto.getDishesClassId());
             dishesInfoMapper.updateById(dishesInfo);
        }else {
            dishesInfo = new DishesInfo();
            dishesInfo.setDishesId(dishes.getId());
            dishesInfo.setDishesClassId(savaDishesDto.getDishesClassId());
            dishesInfo.setCreatorTime(new Date());
            dishesInfo.setCreatorUserId(CurrentUserUtil.getUserId());
            dishesInfo.setCreatorUser(CurrentUserUtil.getUserName());
            dishesInfoMapper.insert(dishesInfo);
        }

    }
}
