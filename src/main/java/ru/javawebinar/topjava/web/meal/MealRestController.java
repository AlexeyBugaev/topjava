package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;
import java.util.List;


public class MealRestController {
    private MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }

    public Collection<Meal> getAll(){
        return service.getAll();
    }

    public Meal create(Meal meal){
        return service.create(meal);
    }

    public MealWithExceed get(int id){
        MealWithExceed mealWithExceed = null;
        List<MealWithExceed> mealsWithExceedList = MealsUtil.getWithExceeded(getAll(),MealsUtil.DEFAULT_CALORIES_PER_DAY);
        for (MealWithExceed meal:mealsWithExceedList) {
            if(meal.getId().equals(id)) mealWithExceed = meal;
        }
        return mealWithExceed;
    }

    public void update(Meal meal){
        service.update(meal);
    }

    public void delete(int id){
        service.delete(id);
    }
}