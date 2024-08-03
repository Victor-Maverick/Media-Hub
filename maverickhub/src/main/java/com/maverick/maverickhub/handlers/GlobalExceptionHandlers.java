package com.maverick.maverickhub.handlers;

import com.maverick.maverickhub.exceptions.MediaUploadFailedException;
import com.maverick.maverickhub.exceptions.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

//@ControllerAdvice used to send error pages when creating mvc apps
@RestControllerAdvice
public class GlobalExceptionHandlers {

    @ExceptionHandler(MediaUploadFailedException.class)
    @ResponseBody
    public ResponseEntity<?> handleMediaUploadFailed(MediaUploadFailedException exception){
        return ResponseEntity.status(BAD_REQUEST).body(Map.of(
                "error", exception.getMessage(),
                "success", false));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFound(UserNotFoundException exception){
        return ResponseEntity.status(BAD_REQUEST)
                .body(Map.of("error", exception.getMessage(),
                        "success", false));
    }
}
