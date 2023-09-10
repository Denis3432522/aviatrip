package com.example.aviatrip.config.exception;

import org.springframework.security.core.AuthenticationException;

public class UserAlreadyAuthenticatedException extends AuthenticationException {

    public UserAlreadyAuthenticatedException(String message) {
        super(message);
    }
}
