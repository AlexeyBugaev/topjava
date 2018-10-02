package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        List<UserMealWithExceed> result = getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
        for (UserMealWithExceed meal : result) {
            System.out.println(meal.toString());
        }
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {


        List<UserMeal> mealListWithinTime = new ArrayList<>();
        Map<LocalDate,Integer> map = new HashMap<>();

        for (UserMeal meal : mealList) {
            LocalTime localTime = meal.getDateTime().toLocalTime();

            if (TimeUtil.isBetween(localTime, startTime, endTime)) mealListWithinTime.add(meal);

            map.put(meal.getDateTime().toLocalDate(), map.getOrDefault(meal.getDateTime().toLocalDate(),0)+ meal.getCalories());
        }

        List<UserMealWithExceed> result = new ArrayList<>();

        for (UserMeal userMeal : mealListWithinTime) {
            int calories = map.get(userMeal.getDateTime().toLocalDate());
            result.add(new UserMealWithExceed(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), calories > caloriesPerDay));
        }
        return result;
    }
}

