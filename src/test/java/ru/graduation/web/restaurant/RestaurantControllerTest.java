package ru.graduation.web.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.graduation.model.Restaurant;
import ru.graduation.repository.RestaurantRepository;
import ru.graduation.util.JsonUtil;
import ru.graduation.web.AbstractControllerTest;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.graduation.web.GlobalExceptionHandler.EXCEPTION_DUPLICATE_PLACE;
import static ru.graduation.web.restaurant.RestaurantTestData.*;
import static ru.graduation.web.restaurant.RestaurantTestData.getUpdated;
import static ru.graduation.web.user.UserTestData.ADMIN_MAIL;
import static ru.graduation.web.user.UserTestData.USER_MAIL;

class RestaurantControllerTest extends AbstractControllerTest {
    private static final String API_URL = RestaurantController.REST_URL;

    @Autowired
    private RestaurantRepository repository;

    @Test
    @WithUserDetails(value = USER_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(API_URL + "/" + REST_ID1))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(rest1));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(API_URL + "/" + NOT_FOUND))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(API_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(rest1, rest2, rest3));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getWithMeals() throws Exception {
        perform(MockMvcRequestBuilders.get(API_URL + "/" + REST_ID1 + "/withDishes"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_WITH_MEALS_MATCHER.contentJson(rest1));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void create() throws Exception {
        Restaurant newRestaurant = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newRestaurant)))
                .andDo(print())
                .andExpect(status().isCreated());

        Restaurant created = RESTAURANT_MATCHER.readFromJson(action);
        int newId = created.id();
        newRestaurant.setId(newId);
        RESTAURANT_MATCHER.assertMatch(created, newRestaurant);
        RESTAURANT_MATCHER.assertMatch(repository.getExisted(newId), newRestaurant);

    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createInvalid() throws Exception {
        Restaurant newRestaurant = new Restaurant(null, null, null);
        perform(MockMvcRequestBuilders.post(API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newRestaurant)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createDuplicate() throws Exception {
        Restaurant newRestaurant = new Restaurant(null, rest1.getName(), rest1.getAddress());
        perform(MockMvcRequestBuilders.post(API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newRestaurant)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(content().string(containsString(EXCEPTION_DUPLICATE_PLACE)));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void createForbidden() throws Exception {
        Restaurant newRestaurant = getNew();
        perform(MockMvcRequestBuilders.post(API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newRestaurant)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void update() throws Exception {
        Restaurant updateRestaurant = getUpdated();
        perform(MockMvcRequestBuilders.put(API_URL + "/" + REST_ID1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updateRestaurant)))
                .andDo(print())
                .andExpect(status().isNoContent());

        RESTAURANT_MATCHER.assertMatch(repository.getExisted(REST_ID1), getUpdated());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateNotFound() throws Exception {
        Restaurant updateRestaurant = getUpdated();
        updateRestaurant.setId(NOT_FOUND);
        perform(MockMvcRequestBuilders.put(API_URL + "/" + REST_ID1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updateRestaurant)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateInvalid() throws Exception {
        Restaurant updateRestaurant = getUpdated();
        updateRestaurant.setAddress("");
        perform(MockMvcRequestBuilders.put(API_URL + "/" + REST_ID1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updateRestaurant)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    @Transactional(propagation = Propagation.NEVER)
    void updateDuplicate() throws Exception {
        Restaurant updateRestaurant = new Restaurant(REST_ID1, rest2.getName(), rest2.getAddress());
        perform(MockMvcRequestBuilders.put(API_URL + "/" + REST_ID1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updateRestaurant)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(content().string(containsString(EXCEPTION_DUPLICATE_PLACE)));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateForbidden() throws Exception {
        Restaurant updateRestaurant = getUpdated();
        perform(MockMvcRequestBuilders.put(API_URL + "/" + REST_ID1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updateRestaurant)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(API_URL + "/" + REST_ID1))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertFalse(repository.findById(REST_ID1).isPresent());
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
        perform(MockMvcRequestBuilders.delete(API_URL + "/" + NOT_FOUND))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}