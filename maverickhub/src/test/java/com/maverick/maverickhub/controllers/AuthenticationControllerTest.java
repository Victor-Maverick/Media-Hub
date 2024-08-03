package com.maverick.maverickhub.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maverick.maverickhub.dtos.requests.LoginRequest;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    @Sql(scripts = {"/db/data.sql"})
    public void authenticateUserTest() throws Exception {
        LoginRequest request = new LoginRequest();
        ObjectMapper mapper = new ObjectMapper();
        request.setEmail("victormsonter@gmail.com");
        request.setPassword("password");
        mockMvc.perform(post("/api/v1/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(request)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Sql(scripts = {"/db/data.sql"})
    public void testThatLoginAuthenticationFailsForIncorrectCredentials() throws Exception {
        LoginRequest request = new LoginRequest();
        ObjectMapper mapper = new ObjectMapper();
        request.setEmail("victormsonte@gmail.com");
        request.setPassword("wrong pasword");
        mockMvc.perform(post("/api/v1/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(request)))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }



}
