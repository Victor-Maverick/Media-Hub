package com.maverick.maverickhub.services;

import com.maverick.maverickhub.dtos.requests.SendMailRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class MailServiceTest {
    @Autowired
    private MailService mailService;

    @Test
    public void testSendEmail(){

        SendMailRequest request = new SendMailRequest();
        request.setRecipientEmail("victormsonter@gmail.com");
        request.setSubject("Hello");
        request.setRecipientName("Gagnon");
        request.setContent("<p>Hello from the other side</p>");
        var response = mailService.sendMail(request);
        assertThat(response).isNotNull();
        assertThat(response).containsIgnoringCase("success");
    }
}
