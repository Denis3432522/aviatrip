package com.example.aviatrip.config.springsecurity;

import com.example.aviatrip.config.responsebody.ErrorResponseBody;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import java.io.*;

public class JsonAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        try(BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(response.getOutputStream()))) {
            String errorMsg = new ObjectMapper().writeValueAsString(new ErrorResponseBody("not authenticated"));
            writer.write(errorMsg);
        } catch (IOException e) {
           throw new RuntimeException(e.getMessage());
        }
    }
}