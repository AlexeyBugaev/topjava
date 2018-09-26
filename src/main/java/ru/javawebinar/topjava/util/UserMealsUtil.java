package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

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

        List<UserMealWithExceed> result = new ArrayList<UserMealWithExceed>();
        List<UserMeal> mealListWithinTime = new ArrayList<UserMeal>();
        Map<LocalDate,Integer> map = new HashMap<>();

//Обход листа, переданного как аргумент

        for (UserMeal meal : mealList) {
            int calories = 0;
            LocalTime localTime = meal.getDateTime().toLocalTime();

//Фильтр листа по времени startTime, endTime

            if (TimeUtil.isBetween(localTime, startTime, endTime)) {
                mealListWithinTime.add(meal);

//Заполнение map с суммой калорий по датам

                if (map.containsKey(meal.getDateTime().toLocalDate())){
                    calories = map.get(meal.getDateTime().toLocalDate());
                    map.replace(meal.getDateTime().toLocalDate(), calories+meal.getCalories());
                }
                else map.put(meal.getDateTime().toLocalDate(), meal.getCalories());
            }
        }

//Заполнение листа UserMealWithExceed, exceed определяем по значению map для ключа в виде даты по каждому meal

        for (UserMeal userMeal : mealListWithinTime) {
            int calories = map.get(userMeal.getDateTime().toLocalDate());
            if(calories > caloriesPerDay) result.add(new UserMealWithExceed(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), true));
            else result.add(new UserMealWithExceed(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), false));
        }

        return result;
    }
}

