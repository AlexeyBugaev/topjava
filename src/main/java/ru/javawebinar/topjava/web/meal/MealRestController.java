package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import java.util.List;

@Controller
public class MealRestController {

    @Autowired
    private MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }

    public List<MealWithExceed> getAll(){
        return service.getAll();
    }

    public Meal create(Meal meal){
        return service.create(meal);
    }

    public MealWithExceed get(int id){
        return service.get(id);
    }

    public void update(Meal meal){
        service.update(meal);
    }

    public void delete(int id){
        service.delete(id);
    }
}