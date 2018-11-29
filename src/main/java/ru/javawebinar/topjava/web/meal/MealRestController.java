package ru.javawebinar.topjava.web.meal;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping(MealRestController.REST_URL)
public class MealRestController extends AbstractMealController {
    static final String REST_URL = "/rest/meals";

    @Override
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Meal get(@PathVariable("id") int id) {
        return super.get(id);
    }

    @Override
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealTo> getAll() {
        return super.getAll();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Meal> createNewMeal(@RequestBody Meal meal) {
        Meal mealCreated = super.create(meal);
        return new ResponseEntity<>(mealCreated, HttpStatus.CREATED);
    }

    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody Meal meal, @PathVariable("id") int id) {
        super.update(meal, id);
    }


    @GetMapping(value = "/between", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealTo> getBetween(HttpServletRequest request) {
        final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ISO_TIME;
        LocalDate startDate = LocalDate.parse(request.getParameter("startDate"), DATE_FORMAT);
        LocalDate endDate = LocalDate.parse(request.getParameter("endDate"), DATE_FORMAT);
        LocalTime startTime = LocalTime.parse(request.getParameter("startTime"), TIME_FORMAT);
        LocalTime endTime = LocalTime.parse(request.getParameter("endTime"), TIME_FORMAT);
        return super.getBetween(startDate, startTime, endDate, endTime);
    }
}