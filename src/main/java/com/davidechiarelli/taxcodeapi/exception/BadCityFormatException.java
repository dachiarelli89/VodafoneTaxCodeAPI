package com.davidechiarelli.taxcodeapi.exception;

/**
 * Exception used to manage errors on city objects
 */

public class BadCityFormatException extends RuntimeException{
    public BadCityFormatException(String message){
        super(message);
    }
}
