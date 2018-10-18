package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        // treat case: update, but absent in storage
        return meal.getUserId().equals(SecurityUtil.authUserId())? repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal) : null;
    }

    @Override
    public boolean delete(int id) {
        if(repository.get(id)!=null){
            repository.remove(id);
            return true;
        }
        else return false;
    }

    @Override
    public Meal get(int id) {
        Meal meal = null;
        if(repository.containsKey(id)){
            if(repository.get(id).getUserId().equals(SecurityUtil.authUserId())) meal = repository.get(id);
        }
        return meal;
    }

    @Override
    public List<Meal> getAll() {
        List<Meal> mealList = new ArrayList<>(repository.values());
        mealList.sort((o1, o2) -> o2.getDate().compareTo(o1.getDate()));
        return mealList;
    }
}

