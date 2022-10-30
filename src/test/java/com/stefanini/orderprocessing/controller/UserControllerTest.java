package com.stefanini.orderprocessing.controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.stefanini.orderprocessing.domain.User;
import com.stefanini.orderprocessing.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.CoreMatchers.is;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private UserController userController;
    @MockBean
    private UserServiceImpl userService;
    @Autowired
    private MockMvc mockMvc;


    @Test
    public void userControllerIsNotNull() throws Exception {
        assertThat(userController).isNotNull();
    }

    @Test
    public void shouldFetchAllUsers() throws Exception {
        List<User> userList = new ArrayList<>();
        User john = new User(1, "John", "str MariaBiesu 12", "howtodoinjava@gmail.com");
        User alex = new User(2, "Alex", "str MariaBiesu 12", "alexk@yahoo.com");
        User steve = new User(3, "Steve", "str MariaBiesu 12", "swaugh@gmail.com");

        userList.add(john);
        userList.add(alex);
        userList.add(steve);

        when(userService.getAllUsers()).thenReturn(userList);
        this.mockMvc.perform(get("/api/user/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(userList.size())));
    }


}
