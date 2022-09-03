package ru.graduation.web.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.graduation.model.User;
import ru.graduation.repository.UserRepository;
import ru.graduation.to.UserTo;
import ru.graduation.util.JsonUtil;
import ru.graduation.util.UserUtil;
import ru.graduation.util.Util;
import ru.graduation.web.AbstractControllerTest;

import java.time.LocalTime;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.graduation.web.user.UniqueMailValidator.EXCEPTION_DUPLICATE_EMAIL;
import static ru.graduation.web.user.UserTestData.*;

class ProfileControllerTest extends AbstractControllerTest {

    private static final String API_URL = ProfileController.REST_URL;

    @Autowired
    private UserRepository userRepository;

    @Test
    @WithUserDetails(value = USER_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(API_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_MATCHER.contentJson(user));
    }

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(API_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(API_URL))
                .andExpect(status().isNoContent());
        USER_MATCHER.assertMatch(userRepository.findAll(), admin, guest);
    }

    @Test
    void register() throws Exception {
        UserTo newTo = new UserTo(null, "newName", "newemail@ya.ru", "newPassword");
        User newUser = UserUtil.createNewFromTo(newTo);
        ResultActions action = perform(MockMvcRequestBuilders.post(API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonWithPassword(newUser, "newPass")))
                .andDo(print())
                .andExpect(status().isCreated());

        User created = USER_MATCHER.readFromJson(action);
        int newId = created.id();
        newUser.setId(newId);
        USER_MATCHER.assertMatch(created, newUser);
        USER_MATCHER.assertMatch(userRepository.getExisted(newId), newUser);
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void update() throws Exception {
        UserTo updateUserTo = new UserTo(USER_ID, "newName", "newemail@ya.ru", "newPassword");
        User uapdateUser = UserUtil.createNewFromTo(updateUserTo);
        perform(MockMvcRequestBuilders.put(API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonWithPassword(uapdateUser, "newPassword")))
                .andDo(print())
                .andExpect(status().isNoContent());

        USER_MATCHER.assertMatch(userRepository.getExisted(USER_ID), UserUtil.updateFromTo(new User(user), updateUserTo));
    }

    @Test
    void registerInvalid() throws Exception {
        UserTo newTo = new UserTo(null, null, null, null);
        perform(MockMvcRequestBuilders.post(API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newTo)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateInvalid() throws Exception {
        UserTo updatedTo = new UserTo(null, null, "password", null);
        perform(MockMvcRequestBuilders.put(API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedTo)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateDuplicate() throws Exception {
        UserTo updatedTo = new UserTo(null, "newName", ADMIN_MAIL, "newPassword");
        perform(MockMvcRequestBuilders.put(API_URL).contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedTo)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(containsString(EXCEPTION_DUPLICATE_EMAIL)));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void voteAndRevoteInTime() throws Exception {
        LocalTime time = LocalTime.now();
        if (!Util.isBetweenHalfOpen(time)) {
            user.setVoteIdRestaurant(1);
            perform(MockMvcRequestBuilders.patch(API_URL)
                    .param("restId", "1")
                    .param("voteIdRestaurant", "true")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isNoContent());

            USER_MATCHER.assertMatch(userRepository.getExisted(USER_ID), user);

            user.setVoteIdRestaurant(0);
            perform(MockMvcRequestBuilders.patch(API_URL)
                    .param("restId", "1")
                    .param("voteIdRestaurant", "false")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isNoContent());

            USER_MATCHER.assertMatch(userRepository.getExisted(USER_ID), user);
        } else {
            perform(MockMvcRequestBuilders.patch(API_URL)
                    .param("restId", "1")
                    .param("voteIdRestaurant", "true")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isConflict())
                    .andExpect(content().string(containsString("You can`t vote since 11:00 to 15:00")));
        }
    }

    @Test
    void voteForbidden() throws Exception {
        perform(MockMvcRequestBuilders.patch(API_URL)
                .param("restId", "1")
                .param("voteIdRestaurant", "true")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
}
