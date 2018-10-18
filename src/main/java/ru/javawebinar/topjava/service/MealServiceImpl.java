package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import java.util.ArrayList;
import java.util.List;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealServiceImpl implements MealService {

    @Autowired
    private MealRepository repository;

    private List<MealWithExceed> mealWithExceedList = new ArrayList<>();

    public MealServiceImpl(MealRepository repository) {
        this.repository = repository;
    }

    @Override
    public Meal create(Meal meal) {
        return repository.save(meal);
    }

    @Override
    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id),id);
    }

    @Override
    public MealWithExceed get(int id) throws NotFoundException {
        getAll();
        for (MealWithExceed mealWithExceed : mealWithExceedList) {
            if(mealWithExceed.getId()==id) return mealWithExceed;
        }
        return null;
    }

    @Override
    public void update(Meal meal) {
        checkNotFoundWithId(repository.save(meal),meal.getId());
    }

    @Override
    public List<MealWithExceed> getAll() {
        mealWithExceedList = MealsUtil.getWithExceeded(repository.getAll(),MealsUtil.DEFAULT_CALORIES_PER_DAY);
        return mealWithExceedList;
    }
}