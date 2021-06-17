package com.davidechiarelli.taxcodeapi.exception;

public class BadDateFormatException extends RuntimeException{
    public BadDateFormatException(String message, Exception e){
        super(message, e);
    }
}
