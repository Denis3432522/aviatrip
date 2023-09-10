package com.example.aviatrip.config.exception;

public class MethodArgumentNotValidFormatException extends BadRequestException {
    private static final String DEFAULT_MESSAGE = "invalid request format";

    public MethodArgumentNotValidFormatException() {
        super(DEFAULT_MESSAGE);
    }

    public MethodArgumentNotValidFormatException(String message) {
        super(message);
    }
}
