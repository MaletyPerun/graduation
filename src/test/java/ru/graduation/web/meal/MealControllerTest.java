package ru.graduation.web.meal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.graduation.model.Meal;
import ru.graduation.repository.MealRepository;
import ru.graduation.util.JsonUtil;
import ru.graduation.web.AbstractControllerTest;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.graduation.web.GlobalExceptionHandler.EXCEPTION_DUPLICATE_MEAL;
import static ru.graduation.web.meal.MealTestData.*;
import static ru.graduation.web.restaurant.RestaurantTestData.REST_ID1;
import static ru.graduation.web.user.UserTestData.ADMIN_MAIL;
import static ru.graduation.web.user.UserTestData.USER_MAIL;

class MealControllerTest extends AbstractControllerTest {
    private static final String API_URL = "/api/restaurants/1/meals";

    @Autowired
    private MealRepository repository;

    @Test
    @WithUserDetails(value = USER_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(API_URL + "/" + MEAL_ID1))
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MEAL_MATCHER.contentJson(meal1));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(API_URL + "/" + NOT_FOUND))
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getNotBelong() throws Exception {
        perform(MockMvcRequestBuilders.get(API_URL + "/" + MEAL_ID2))
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void create() throws Exception {
        Meal newMeal = getNew();
        ResultActions actions = perform(MockMvcRequestBuilders.post(API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newMeal)))
                .andDo(print())
                .andExpect(status().isCreated());

        Meal created = MEAL_MATCHER.readFromJson(actions);
        int newId = created.id();
        newMeal.setId(newId);
        MEAL_MATCHER.assertMatch(created, newMeal);
        MEAL_MATCHER.assertMatch(repository.getBelong(REST_ID1, newId), newMeal);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createInvalid() throws Exception {
        Meal newMeal = new Meal(null, null, null);
        perform(MockMvcRequestBuilders.post(API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newMeal)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createDuplicate() throws Exception {
        Meal newMeal = new Meal(null, meal1.getPrice(), meal1.getDescription());
        perform(MockMvcRequestBuilders.post(API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newMeal)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(content().string(containsString(EXCEPTION_DUPLICATE_MEAL)));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void createForbidden() throws Exception {
        Meal newMeal = getNew();
        perform(MockMvcRequestBuilders.post(API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newMeal)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void update() throws Exception {
        Meal updateMeal = getUpdated();
        perform(MockMvcRequestBuilders.put(API_URL + "/" + MEAL_ID1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updateMeal)))
                .andDo(print())
                .andExpect(status().isNoContent());
        MEAL_MATCHER.assertMatch(repository.getExisted(MEAL_ID1), updateMeal);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateInvalid() throws Exception {
        Meal updateMeal = getUpdated();
        updateMeal.setDescription("");
        perform(MockMvcRequestBuilders.put(API_URL + "/" + MEAL_ID1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updateMeal)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    @Transactional(propagation = Propagation.NEVER)
    void updateDuplicate() throws Exception {
        Meal updateMeal = new Meal(MEAL_ID1, meal6.getPrice(), meal6.getDescription());
        perform(MockMvcRequestBuilders.put(API_URL + "/" + MEAL_ID1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updateMeal)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(content().string(containsString(EXCEPTION_DUPLICATE_MEAL)));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    @Transactional(propagation = Propagation.NEVER)
    void updateNotBelong() throws Exception {
        perform(MockMvcRequestBuilders.put(API_URL + "/" + MEAL_ID2)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(meal2)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(containsString("doesn't belong to Restaurant")));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateNotFound() throws Exception {
        Meal updateMeal = getUpdated();
        updateMeal.setId(NOT_FOUND);
        perform(MockMvcRequestBuilders.put(API_URL + "/" + MEAL_ID1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updateMeal)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateForbidden() throws Exception {
        Meal updateMeal = getUpdated();
        perform(MockMvcRequestBuilders.put(API_URL + "/" + MEAL_ID1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updateMeal)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(API_URL + "/" + MEAL_ID1))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertFalse(repository.findById(MEAL_ID1).isPresent());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(API_URL + "/" + NOT_FOUND))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void deleteForbidden() throws Exception {
        perform(MockMvcRequestBuilders.delete(API_URL + "/" + MEAL_ID1))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}