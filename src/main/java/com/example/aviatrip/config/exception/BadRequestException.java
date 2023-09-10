package com.example.aviatrip.config.exception;

public class BadRequestException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "bad request";

    public BadRequestException() {
        super(DEFAULT_MESSAGE);
    }

    public BadRequestException(String message) {
        super(message);
    }
}
