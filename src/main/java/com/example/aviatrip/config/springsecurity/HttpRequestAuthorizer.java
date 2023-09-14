package com.example.aviatrip.config.springsecurity;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;

public class HttpRequestAuthorizer implements Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> {
    @Override
    public void customize(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry a) {
        a.requestMatchers(HttpMethod.POST, "/api/auth/signup/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/auth/signin").permitAll()
                .anyRequest().authenticated();
    }
}
