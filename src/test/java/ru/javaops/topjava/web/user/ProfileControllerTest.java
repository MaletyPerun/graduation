package ru.javaops.topjava.web.user;

import ru.javaops.topjava.web.AbstractControllerTest;

class ProfileControllerTest extends AbstractControllerTest {

//    @Autowired
//    private UserRepository userRepository;
//
//    @Test
//    @WithUserDetails(value = USER_MAIL)
//    void get() throws Exception {
//        perform(MockMvcRequestBuilders.get(REST_URL))
//                .andExpect(status().isOk())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(USER_MATCHER.contentJson(user));
//    }
//
//    @Test
//    void getUnAuth() throws Exception {
//        perform(MockMvcRequestBuilders.get(REST_URL))
//                .andExpect(status().isUnauthorized());
//    }
//
//    @Test
//    @WithUserDetails(value = USER_MAIL)
//    void delete() throws Exception {
//        perform(MockMvcRequestBuilders.delete(REST_URL))
//                .andExpect(status().isNoContent());
//        USER_MATCHER.assertMatch(userRepository.findAll(), admin, guest);
//    }
//
//    @Test
//    void register() throws Exception {
//        UserTo newTo = new UserTo(null, "newName", "newemail@ya.ru", "newPassword", 1500);
//        User newUser = UserUtil.createNewFromTo(newTo);
//        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(JsonUtil.writeValue(newTo)))
//                .andDo(print())
//                .andExpect(status().isCreated());
//
//        User created = USER_MATCHER.readFromJson(action);
//        int newId = created.id();
//        newUser.setId(newId);
//        USER_MATCHER.assertMatch(created, newUser);
//        USER_MATCHER.assertMatch(userRepository.getExisted(newId), newUser);
//    }
//
//    @Test
//    @WithUserDetails(value = USER_MAIL)
//    void update() throws Exception {
//        UserTo updatedTo = new UserTo(null, "newName", USER_MAIL, "newPassword", 1500);
//        perform(MockMvcRequestBuilders.put(REST_URL).contentType(MediaType.APPLICATION_JSON)
//                .content(JsonUtil.writeValue(updatedTo)))
//                .andDo(print())
//                .andExpect(status().isNoContent());
//
//        USER_MATCHER.assertMatch(userRepository.getExisted(USER_ID), UserUtil.updateFromTo(new User(user), updatedTo));
//    }
//
//    @Test
//    void registerInvalid() throws Exception {
//        UserTo newTo = new UserTo(null, null, null, null, 1);
//        perform(MockMvcRequestBuilders.post(REST_URL)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(JsonUtil.writeValue(newTo)))
//                .andDo(print())
//                .andExpect(status().isUnprocessableEntity());
//    }
//
//    @Test
//    @WithUserDetails(value = USER_MAIL)
//    void updateInvalid() throws Exception {
//        UserTo updatedTo = new UserTo(null, null, "password", null, 1500);
//        perform(MockMvcRequestBuilders.put(REST_URL)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(JsonUtil.writeValue(updatedTo)))
//                .andDo(print())
//                .andExpect(status().isUnprocessableEntity());
//    }
//
//    @Test
//    @WithUserDetails(value = USER_MAIL)
//    void updateDuplicate() throws Exception {
//        UserTo updatedTo = new UserTo(null, "newName", ADMIN_MAIL, "newPassword", 1500);
//        perform(MockMvcRequestBuilders.put(REST_URL).contentType(MediaType.APPLICATION_JSON)
//                .content(JsonUtil.writeValue(updatedTo)))
//                .andDo(print())
//                .andExpect(status().isUnprocessableEntity())
//                .andExpect(content().string(containsString(EXCEPTION_DUPLICATE_EMAIL)));
//    }
}