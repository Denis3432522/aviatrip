package com.example.aviatrip.config.springsecurity;

import com.example.aviatrip.model.response.ErrorResponseModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class JsonAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        try(BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(response.getOutputStream()))) {
            String errorMsg = new ObjectMapper().writeValueAsString(new ErrorResponseModel("not authenticated"));
            writer.write(errorMsg);
        } catch (IOException e) {
           throw new RuntimeException(e.getMessage());
        }
    }
}
