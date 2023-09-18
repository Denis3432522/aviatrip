package com.example.aviatrip.config.exception;

public class ValueNotExistException extends BadRequestException {

    private static final String MESSAGE_POSTFIX = " doesn't exist";
    private static final String DEFAULT_MESSAGE = "value" + MESSAGE_POSTFIX;

    public ValueNotExistException() {
        super(DEFAULT_MESSAGE);
    }

    public ValueNotExistException(String message, boolean useMessagePostfix) {
        super(useMessagePostfix ? message + MESSAGE_POSTFIX : message);
    }
}
