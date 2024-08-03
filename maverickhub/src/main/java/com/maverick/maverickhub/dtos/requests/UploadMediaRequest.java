package com.maverick.maverickhub.dtos.requests;

import com.maverick.maverickhub.models.Category;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
public class UploadMediaRequest {
    private MultipartFile mediaFile;
    private String description;
    private Category category;
    private Long userId;
}
