package com.maverick.maverickhub.security.services;

import com.maverick.maverickhub.exceptions.UserNotFoundException;
import com.maverick.maverickhub.models.User;
import com.maverick.maverickhub.security.models.SecureUser;
import com.maverick.maverickhub.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = userService.getByUsername(username);
            return new SecureUser(user);
        }catch (UserNotFoundException e) {
            throw new UsernameNotFoundException(e.getMessage());
        }

    }
}
