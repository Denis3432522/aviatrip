package com.example.aviatrip.config.filter;

import com.example.aviatrip.config.springsecurity.UserSessionManager;
import com.example.aviatrip.model.request.auth.AuthenticationModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;

public class JsonUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    @Autowired
    private UserSessionManager sessionManager;

    public JsonUsernamePasswordAuthenticationFilter() {
        super(new AntPathRequestMatcher("/auth/signin", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        sessionManager.assertNotAuthenticated();

        AuthenticationModel authModel;
        try {
            authModel = new ObjectMapper().readValue(request.getInputStream(), AuthenticationModel.class);
            if(authModel.email() == null || authModel.email().isEmpty() || authModel.password() == null || authModel.password().isEmpty())
                throw new IOException();

        } catch (IOException e) {
            throw new AuthenticationServiceException("invalid format payload");
        }

        var token = new UsernamePasswordAuthenticationToken(authModel.email(), authModel.password());
        token.setDetails(this.authenticationDetailsSource.buildDetails(request));

        return super.getAuthenticationManager().authenticate(token);
    }
}
