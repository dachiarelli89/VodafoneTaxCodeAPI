package com.davidechiarelli.taxcodeapi.exception;

/**
 * Exception used to manage errors on date format
 */

public class BadDateFormatException extends RuntimeException{
    public BadDateFormatException(String message, Exception e){
        super(message, e);
    }
}
