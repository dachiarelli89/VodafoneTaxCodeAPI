package com.davidechiarelli.taxcodeapi.exception;

/**
 * Exception used to manage semantic errors on request objects
 */

public class UnprocessableDataException extends RuntimeException {
    public UnprocessableDataException(String message) {
        super(message);
    }
}
