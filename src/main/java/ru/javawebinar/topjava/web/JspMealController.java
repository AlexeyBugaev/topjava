package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;
import static ru.javawebinar.topjava.util.Util.orElse;

@Controller
@RequestMapping("/meals")
public class JspMealController {

    @Autowired
    private MealService service;

    @GetMapping(value = "/create")
    public String create(Model model) {
        int userId = SecurityUtil.authUserId();
        Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        model.addAttribute("meal", meal);
        int par = 1;
        model.addAttribute("par", par);
        return "mealForm";
    }

    @GetMapping(value = "/update/{id}")
    public String update(@PathVariable("id") int id, Model model) {
        int userId = SecurityUtil.authUserId();
        Meal meal = service.get(id, userId);
        model.addAttribute("meal", meal);
        service.update(meal, userId);
        int par = 2;
        model.addAttribute("par", par);
        return "mealForm";
    }

    @GetMapping(value = "/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        int userId = SecurityUtil.authUserId();
        service.delete(id, userId);
        return "redirect:/meals";
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("meals", MealsUtil.getWithExcess(service.getAll(SecurityUtil.authUserId()), SecurityUtil.authUserCaloriesPerDay()));
        return "meals";
    }

    @PostMapping(value = "/save")
    public String save(HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

        if (request.getParameter("id").isEmpty()) {
            service.create(meal, SecurityUtil.authUserId());
        } else {
            service.update(meal, SecurityUtil.authUserId());
        }
        return "redirect:/meals";
    }

    @PostMapping(value = "/filter")
    public String filter(HttpServletRequest request) {
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
        request.setAttribute("meals", getBetween(startDate, startTime, endDate, endTime));
        return "meals";
    }

    public List<MealTo> getBetween(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        int userId = SecurityUtil.authUserId();

        List<Meal> mealsDateFiltered = service.getBetweenDates(
                orElse(startDate, DateTimeUtil.MIN_DATE), orElse(endDate, DateTimeUtil.MAX_DATE), userId);

        return MealsUtil.getFilteredWithExcess(mealsDateFiltered, SecurityUtil.authUserCaloriesPerDay(),
                orElse(startTime, LocalTime.MIN), orElse(endTime, LocalTime.MAX)
        );
    }
}
