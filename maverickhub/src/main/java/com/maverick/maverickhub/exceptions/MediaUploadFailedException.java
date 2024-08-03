package com.maverick.maverickhub.exceptions;

public class MediaUploadFailedException extends RuntimeException{
    public MediaUploadFailedException(String message){
        super(message);
    }
}
