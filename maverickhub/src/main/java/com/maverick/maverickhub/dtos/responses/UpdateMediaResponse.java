package com.maverick.maverickhub.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maverick.maverickhub.models.Category;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class UpdateMediaResponse {
    private Long id;
    private String url;
    private String description;
    private Category category;
    @JsonProperty("time_created")
    private LocalDateTime timeCreated;
    @JsonProperty("time_updated")
    private LocalDateTime timeUpdated;
}
