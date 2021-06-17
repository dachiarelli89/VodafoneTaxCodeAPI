package com.davidechiarelli.taxcodeapi.exception;

public class BadCityFormatException extends RuntimeException{
    public BadCityFormatException(String message){
        super(message);
    }
}
