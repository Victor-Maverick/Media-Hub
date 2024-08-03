package com.maverick.maverickhub.services;

import com.maverick.maverickhub.dtos.requests.CreateUserRequest;
import com.maverick.maverickhub.dtos.responses.CreateUserResponse;
import com.maverick.maverickhub.exceptions.UserNotFoundException;
import com.maverick.maverickhub.models.Authority;
import com.maverick.maverickhub.models.User;
import com.maverick.maverickhub.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@AllArgsConstructor
public class MaverickUserService implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public CreateUserResponse register(CreateUserRequest request) {
        User user = modelMapper.map(request, User.class);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setAuthorities(new HashSet<>());
        var authorities = user.getAuthorities();
        authorities.add(Authority.USER);
        userRepository.save(user);
        var response = modelMapper.map(user, CreateUserResponse.class);
        response.setMessage("User registered successfully");
        return response;
    }

    @Override
    public User getById(long id) {
        return userRepository.findById(id).orElseThrow(()->new UserNotFoundException(String.format("user with id %d not found", id)));
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.findByEmail(username)
                .orElseThrow(()->new UserNotFoundException
                        (String.format("user with email %s not found", username)));
    }
}
