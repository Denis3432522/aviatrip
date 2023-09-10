package com.example.aviatrip.config.exception;

public class ValueNotUniqueException extends BadRequestException {

    private static final String MESSAGE_POSTFIX = " already taken";
    private static final String DEFAULT_MESSAGE = "value" + MESSAGE_POSTFIX;

    public ValueNotUniqueException() {
        super(DEFAULT_MESSAGE);
    }

    public ValueNotUniqueException(String message, boolean useMessagePostfix) {
        super(useMessagePostfix ? message + MESSAGE_POSTFIX : message);
    }
}
