package com.maverick.maverickhub.dtos.responses;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UploadMediaResponse {
    private Long mediaId;
    private String mediaUrl;
    private String description;
}
