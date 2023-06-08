package com.orderingMinAppAip.service;


import com.orderingMinAppAip.dto.dishes.SavaDishesDto;
import com.orderingMinAppAip.model.dishes.Dishes;

public interface DishesService {

    void upDishesImg(SavaDishesDto savaDishesDto, Dishes dishes);

    void upDisheSon(SavaDishesDto savaDishesDto, Dishes dishes);


    void upDisheClass(SavaDishesDto savaDishesDto, Dishes dishes) throws Exception;
}
