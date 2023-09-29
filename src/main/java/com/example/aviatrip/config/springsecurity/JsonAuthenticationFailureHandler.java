package com.example.aviatrip.config.springsecurity;

import com.example.aviatrip.config.exception.UserAlreadyAuthenticatedException;
import com.example.aviatrip.model.response.ErrorResponseModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import java.io.IOException;
import java.io.Writer;

public class JsonAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex) {
        if(ex instanceof UserAlreadyAuthenticatedException)
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        else
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        try(Writer writer = response.getWriter()) {
            String errorMsg = new ObjectMapper().writeValueAsString(new ErrorResponseModel(ex.getMessage()));
            writer.write(errorMsg);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
