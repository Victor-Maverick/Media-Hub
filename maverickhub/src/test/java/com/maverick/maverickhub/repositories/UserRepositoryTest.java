package com.maverick.maverickhub.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Test
    void findByEmail() {
        assertNotNull(userRepository.findByEmail("victormsonter@gmail.com"));

    }
}