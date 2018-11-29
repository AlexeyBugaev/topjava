package ru.javawebinar.topjava.web.meal;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.time.Month;
import java.util.List;

import static java.time.LocalDateTime.of;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.TestUtil.readFromJson;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;


class MealRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = MealRestController.REST_URL + '/';

    @Test
    void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + MEAL1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(MEAL1));
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + MEAL1_ID))
                .andDo(print())
                .andExpect(status().isOk());
        assertMatch(mealService.getAll(START_SEQ), MEAL6, MEAL5, MEAL4, MEAL3, MEAL2);
    }

    @Test
    void getAll() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1));
    }

    @Test
    void createNewMeal() throws Exception {
        Meal expected = new Meal(null, of(2018, Month.NOVEMBER, 29, 9, 30), "newMeal", 500);
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated());

        Meal returned = readFromJson(action, Meal.class);
        expected.setId(returned.getId());

        assertMatch(returned, expected);
        assertMatch(mealService.getAll(START_SEQ), expected, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1);
    }

    @Test
    void update() throws Exception {
        Meal mealUpdated = new Meal(MEAL1);
        mockMvc.perform(put(REST_URL + MEAL1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(mealUpdated)))
                .andExpect(status().isOk());
        assertMatch(mealService.get(MEAL1_ID, START_SEQ), mealUpdated);
    }

    @Test
    void getBetweenMeals() throws Exception {
        ResultActions action = mockMvc.perform(get(REST_URL + "between?startDate=" + "30-05-2015" + "&startTime=" + "12:00" +
                "&endDate=" + "31-05-2015" + "&endTime=" + "12:00"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        List meals = readFromJson(action, List.class);
        assertMatch(mealService.getBetweenDateTimes(of(2015, Month.MAY, 30, 12, 0), of(2015, Month.MAY, 31, 12, 0), START_SEQ),
                MEAL4, MEAL3, MEAL2);
    }
}