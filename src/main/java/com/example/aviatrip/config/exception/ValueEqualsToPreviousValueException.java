package com.example.aviatrip.config.exception;

public class ValueEqualsToPreviousValueException extends BadRequestException {

    private static final String DEFAULT_MESSAGE_PATTERN = "? must not be equal to previous ?";
    private static final String DEFAULT_MESSAGE = DEFAULT_MESSAGE_PATTERN.replaceAll("\\?", "value");

    public ValueEqualsToPreviousValueException() {
        super(DEFAULT_MESSAGE);
    }

    public ValueEqualsToPreviousValueException(String message, boolean useDefaultPattern) {
        super(useDefaultPattern ? DEFAULT_MESSAGE_PATTERN.replaceAll("\\?", message) : message);
    }
}
