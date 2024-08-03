package com.maverick.maverickhub.controllers;

import com.maverick.maverickhub.dtos.requests.UploadMediaRequest;
import com.maverick.maverickhub.dtos.responses.UploadMediaResponse;
import com.maverick.maverickhub.exceptions.UserNotFoundException;
import com.maverick.maverickhub.services.MediaService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/media")
@AllArgsConstructor
public class MediaController {

    private final MediaService mediaService;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<UploadMediaResponse> uploadMedia(@ModelAttribute UploadMediaRequest request){
        return ResponseEntity.status(CREATED)
                .body(mediaService.upload(request));
    }

    @GetMapping
    public ResponseEntity<?> getMediaForUser(@RequestParam Long userId) throws UserNotFoundException {
        return ResponseEntity.ok().body(mediaService.getMediaForUser(userId));
    }
}
