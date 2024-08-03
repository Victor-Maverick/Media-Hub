package com.maverick.maverickhub.services;

import com.maverick.maverickhub.dtos.requests.CreateUserRequest;
import com.maverick.maverickhub.dtos.responses.CreateUserResponse;
import com.maverick.maverickhub.models.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Sql(scripts = {"/db/data.sql"})
public class UserServicesTest {
    @Autowired
    private UserService userService;

    @Test
    public void registerTest(){
        CreateUserRequest request = new CreateUserRequest();
        request.setEmail("test@gmail.com");
        request.setPassword("password");

        CreateUserResponse response = userService.register(request);
        assertNotNull(response);
        assertTrue(response.getMessage().contains("success"));
    }

    @Test
    @DisplayName("test that user can be gotten by id")
    public void testGetUserById(){
        User user = userService.getById(200L);
        assertNotNull(user.getId());
    }
}
