package com.example.aviatrip.config.exceptionhandlers;

import com.example.aviatrip.config.exception.BadRequestException;
import com.example.aviatrip.config.exception.ResourceNotFoundException;
import com.example.aviatrip.config.exception.UserAlreadyAuthenticatedException;
import com.example.aviatrip.model.response.ErrorResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class DefaultExceptionHandlers {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleNotValidMethodArgument(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getFieldErrors()
                .forEach(e -> errors.put(e.getField(), e.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponseModel> handleDefaultBadRequest(BadRequestException ex) {
        return ResponseEntity.badRequest().body(new ErrorResponseModel(ex.getMessage()));
    }

    @ExceptionHandler(UserAlreadyAuthenticatedException.class)
    public ResponseEntity<ErrorResponseModel> handleUserAlreadyAuthenticated(UserAlreadyAuthenticatedException ex) {
        return ResponseEntity.badRequest().body(new ErrorResponseModel(ex.getMessage()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseModel> handleNotReadableHttpMessage() {
        return ResponseEntity.badRequest().body(new ErrorResponseModel("invalid format payload"));
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ErrorResponseModel handleResourceNotFound(ResourceNotFoundException ex) {
        return new ErrorResponseModel(ex.getMessage());
    }
}