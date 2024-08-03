package com.maverick.maverickhub.services;

import com.maverick.maverickhub.dtos.requests.SendMailRequest;

public interface MailService {
    String sendMail(SendMailRequest sendMailRequest);
}
