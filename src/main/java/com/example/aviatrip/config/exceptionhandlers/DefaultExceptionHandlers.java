package com.example.aviatrip.config.exceptionhandlers;

import com.example.aviatrip.config.exception.BadRequestException;
import com.example.aviatrip.config.exception.UserAlreadyAuthenticatedException;
import com.example.aviatrip.config.responsebody.ErrorResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class DefaultExceptionHandlers {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleNotValidMethodArgument(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getFieldErrors()
                .forEach(e -> errors.put(e.getField(), e.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponseBody> handleDefaultBadRequest(BadRequestException ex) {
        return ResponseEntity.badRequest().body(new ErrorResponseBody(ex.getMessage()));
    }

    @ExceptionHandler(UserAlreadyAuthenticatedException.class)
    public ResponseEntity<ErrorResponseBody> handleUserAlreadyAuthenticated(UserAlreadyAuthenticatedException ex) {
        return ResponseEntity.badRequest().body(new ErrorResponseBody(ex.getMessage()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseBody> handleNotReadableHttpMessage() {
        return ResponseEntity.badRequest().body(new ErrorResponseBody("invalid format payload"));
    }
}
