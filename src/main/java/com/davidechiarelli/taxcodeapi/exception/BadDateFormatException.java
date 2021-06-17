package com.davidechiarelli.taxcodeapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadDateFormatException extends RuntimeException{
    public BadDateFormatException(String message, Exception e){
        super(message, e);
    }
}
