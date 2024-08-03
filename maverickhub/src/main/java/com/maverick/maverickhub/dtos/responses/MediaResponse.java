package com.maverick.maverickhub.dtos.responses;

import com.maverick.maverickhub.models.Category;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class MediaResponse {
    //SDLC
    private Long id;
    private String url;
    private String description;
    private Category category;
    private LocalDateTime timeCreated;
    private LocalDateTime timeUpdated;
    private UserResponse uploader;

}
