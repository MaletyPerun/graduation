package ru.graduation.web.vote;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.graduation.model.Vote;
import ru.graduation.repository.VoteRepository;
import ru.graduation.util.JsonUtil;
import ru.graduation.web.AbstractControllerTest;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.graduation.web.GlobalExceptionHandler.EXCEPTION_DUPLICATE_VOTE;
import static ru.graduation.web.restaurant.RestaurantTestData.rest2;
import static ru.graduation.web.user.UserTestData.*;
import static ru.graduation.web.vote.VoteTestData.*;

class VoteControllerTest extends AbstractControllerTest {

    static final String API_URL = VoteController.REST_URL;

    @Autowired
    private VoteRepository repository;

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void create() throws Exception {
        Vote newVote = getNewVote();
        ResultActions actions = perform(MockMvcRequestBuilders.post(API_URL)
                .param("restId", "2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newVote)))
                .andDo(print())
                .andExpect(status().isCreated());


        Vote created = VOTE_MATCHER.readFromJson(actions);
        int newId = created.id();
        newVote.setId(newId);
        newVote.setUser(admin);
        newVote.setRestaurant(rest2);
        VOTE_MATCHER.assertMatch(created, newVote);
        VOTE_MATCHER.assertMatch(repository.getBelong(ADMIN_ID, localDateTest), newVote);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createInvalid() throws Exception {
        Vote newVote = getNewVote();
        perform(MockMvcRequestBuilders.post(API_URL)
                .param("restId", "4")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newVote)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void createForbidden() throws Exception {
        Vote newVote = getNewVote();
        perform(MockMvcRequestBuilders.post(API_URL)
                .param("restId", "2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newVote)))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void createDuplicate() throws Exception {
        Vote newVote = getNewVote();
        perform(MockMvcRequestBuilders.post(API_URL)
                .param("restId", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newVote)))
                .andDo(print())
                .andExpect(content().string(containsString(EXCEPTION_DUPLICATE_VOTE)));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void update() throws Exception {
        Vote updatedVote = getUpdatedVote();
        perform(MockMvcRequestBuilders.put(API_URL + "/" + VOTE_ID1)
                .param("restId", "2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedVote)))
                .andDo(print())
                .andExpect(status().isNoContent());
        VOTE_MATCHER.assertMatch(repository.getExisted(VOTE_ID1), updatedVote);
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateInvalid() throws Exception {
        Vote updateVote = getUpdatedVote();
        perform(MockMvcRequestBuilders.put(API_URL + "/" + VOTE_ID1)
                .param("restId", "4")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updateVote)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void updateForbidden() throws Exception {
        Vote updateVote = getUpdatedVote();
        perform(MockMvcRequestBuilders.put(API_URL + "/" + VOTE_ID1)
                .param("restId", "3")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updateVote)))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateDuplicate() throws Exception {
        Vote updateVote = getUpdatedVote();
        perform(MockMvcRequestBuilders.put(API_URL + "/" + VOTE_ID1)
                .param("restId", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updateVote)))
                .andDo(print())
                .andExpect(content().string(containsString("You have already voted")));
    }
}