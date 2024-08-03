package com.maverick.maverickhub.dtos.responses;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BaseResponse <T>{
    private int code;
    private boolean status;
    private T data;
}
