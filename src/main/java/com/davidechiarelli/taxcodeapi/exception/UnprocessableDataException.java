package com.davidechiarelli.taxcodeapi.exception;

public class UnprocessableDataException extends RuntimeException {
    public UnprocessableDataException(String message) {
        super(message);
    }
}
