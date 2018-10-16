package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    //set authorizedUserId to 0 for testing
    private Integer authorizedUserId =0;

    {
        MealsUtil.MEALS.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()&&meal.getUserId().equals(authorizedUserId)) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        // treat case: update, but absent in storage
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id) {
        if(repository.containsKey(id)){
            if(!repository.get(id).getUserId().equals(authorizedUserId)) return false;
            repository.remove(id);
            return true;
        }
        return false;
    }

    @Override
    public Meal get(int id) {
        Meal meal = null;
        if(repository.containsKey(id)){
            if(repository.get(id).getUserId().equals(authorizedUserId)) meal = repository.get(id);
        }
        return meal;
    }

    @Override
    public Collection<Meal> getAll() {
        List<Meal> mealList = new ArrayList<>(repository.values());
        mealList.sort(new Comparator<Meal>() {
            @Override
            public int compare(Meal o1, Meal o2) {
                return o2.getDate().compareTo(o1.getDate());
            }
        });
        return mealList;
    }
}

