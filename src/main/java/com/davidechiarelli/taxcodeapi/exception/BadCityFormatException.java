package com.davidechiarelli.taxcodeapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadCityFormatException extends RuntimeException{
    public BadCityFormatException(String message){
        super(message);
    }
}
