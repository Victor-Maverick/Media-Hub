package com.maverick.maverickhub.services;

import com.maverick.maverickhub.dtos.requests.CreateUserRequest;
import com.maverick.maverickhub.dtos.responses.CreateUserResponse;
import com.maverick.maverickhub.models.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService {

    CreateUserResponse register(CreateUserRequest request);

    User getById(long userId);
    User getByUsername(String username) throws UsernameNotFoundException;
}
