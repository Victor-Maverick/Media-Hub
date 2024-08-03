package com.maverick.maverickhub.dtos.responses;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserResponse {
    private Long id;
    private String email;
    private LocalDateTime timeCreated;
    private LocalDateTime timeUpdated;
}
