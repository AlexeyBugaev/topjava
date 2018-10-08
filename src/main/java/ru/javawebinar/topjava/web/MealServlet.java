package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");
        List<Meal> meals = Arrays.asList(
                new Meal(LocalDateTime.of(2018, Month.OCTOBER, 8, 7, 0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2018, Month.OCTOBER, 8, 12, 0), "Обед", 1000),
                new Meal(LocalDateTime.of(2018, Month.OCTOBER, 8, 19, 0), "Ужин", 500),
                new Meal(LocalDateTime.of(2018, Month.OCTOBER, 9, 7, 0), "Завтрак", 1000),
                new Meal(LocalDateTime.of(2018, Month.OCTOBER, 9, 12, 0), "Обед", 500),
                new Meal(LocalDateTime.of(2018, Month.OCTOBER, 9, 19, 0), "Ужин", 700)
        );
        request.setAttribute("meals", MealsUtil.getFilteredWithExceededByCycle(meals, LocalTime.of(0,0),LocalTime.of(23,59),2000));
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }
}