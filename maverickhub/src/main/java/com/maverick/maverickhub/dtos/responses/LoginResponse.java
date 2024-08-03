package com.maverick.maverickhub.dtos.responses;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class LoginResponse {
    private String message;
    private String token;
}
