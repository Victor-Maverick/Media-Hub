package com.maverick.maverickhub.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class SendMailRequest {
    private String recipientEmail;
    private String recipientName;
    private String subject;
    private String content;
}
