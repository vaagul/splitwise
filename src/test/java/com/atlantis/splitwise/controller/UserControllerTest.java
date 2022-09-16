package com.atlantis.splitwise.controller;

import com.atlantis.splitwise.AbstractTest;
import com.atlantis.splitwise.controller.UserController;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@Import({UserController.class})
@Slf4j
public class UserControllerTest extends AbstractTest {
    @Autowired
    MockMvc mvc;

    @Autowired
    private UserController userController;

    @Test
    public void contextLoads() throws Exception {
        assertNotNull(userController);
    }

    @Test
    public void getUsers() throws Exception {
        String uri = "/user/all";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertTrue(content.contains("U1"));
        assertTrue(content.contains("U2"));
        assertTrue(content.contains("U3"));
        assertTrue(content.contains("U4"));
        assertTrue(content.contains("U5"));
    }

    @AfterEach
    public void teardownAfterEach() {
    }

}
