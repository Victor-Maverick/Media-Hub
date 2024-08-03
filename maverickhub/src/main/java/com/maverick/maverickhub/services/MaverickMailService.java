package com.maverick.maverickhub.services;

import com.maverick.maverickhub.config.MailConfig;
import com.maverick.maverickhub.dtos.requests.BrevoMailRequest;
import com.maverick.maverickhub.dtos.requests.Recipient;
import com.maverick.maverickhub.dtos.requests.SendMailRequest;
import com.maverick.maverickhub.dtos.requests.Sender;
import com.maverick.maverickhub.dtos.responses.BrevoMailResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
@AllArgsConstructor
public class MaverickMailService implements MailService{
    private final MailConfig mailConfig;

    @Override
    public String sendMail(SendMailRequest sendMailRequest) {
        RestTemplate restTemplate = new RestTemplate();
        String url = mailConfig.getMailApiUrl();
        BrevoMailRequest request = new BrevoMailRequest();
        request.setSubject(sendMailRequest.getSubject());
        request.setSender(new Sender());
        request.setRecipients(
                List.of(
                new Recipient(sendMailRequest.getRecipientEmail(), sendMailRequest.getRecipientName())
        ));
        request.setContent(sendMailRequest.getContent());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        headers.set("api-key", mailConfig.getMailApiKey());
        headers.set("accept", APPLICATION_JSON.toString());
        RequestEntity<?> httpRequest
                = new RequestEntity<>(request, headers, HttpMethod.POST, URI.create(url));
        ResponseEntity<BrevoMailResponse>response = restTemplate.postForEntity(url, httpRequest, BrevoMailResponse.class);
        if(response.getBody() != null
                && response.getStatusCode()== HttpStatusCode.valueOf(201))
            return "mail sent successfully";
        else throw new RuntimeException("email not sent");


    }

}
