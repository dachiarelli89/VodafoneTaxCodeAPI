package com.davidechiarelli.taxcodeapi.exception;

/**
 * Exception used to manage generic errors on request object
 */

public class BadRequestException extends RuntimeException{
    public BadRequestException(String message){
        super(message);
    }
}
