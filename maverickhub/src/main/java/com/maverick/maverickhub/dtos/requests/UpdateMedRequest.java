package com.maverick.maverickhub.dtos.requests;

import com.maverick.maverickhub.models.Category;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateMedRequest {
    private String description;
    private Category category;
}
