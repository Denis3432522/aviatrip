package com.example.aviatrip.config.springsecurity;

import com.example.aviatrip.config.filter.JsonUsernamePasswordAuthenticationFilter;
import com.example.aviatrip.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.session.*;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

import java.util.Arrays;


@Configuration
@EnableWebSecurity
@ComponentScan("com.example.aviatrip")
public class WebSecurityConfiguration {

    private final UserRepository userRepository;

    public WebSecurityConfiguration(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterAt(jsonUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(httpRequestAuthorizer())
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .exceptionHandling(c -> c.authenticationEntryPoint(jsonAuthenticationEntryPoint()));
        return http.build();
    }

    @Bean
    protected Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry>
        httpRequestAuthorizer() {
        return new HttpRequestAuthorizer();
    }

    @Bean
    protected JsonUsernamePasswordAuthenticationFilter jsonUsernamePasswordAuthenticationFilter() {
        JsonUsernamePasswordAuthenticationFilter authFilter = new JsonUsernamePasswordAuthenticationFilter();
        authFilter.setAuthenticationManager(customAuthenticationManager());
        authFilter.setSessionAuthenticationStrategy(sessionAuthenticationStrategy());
        authFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler());
        authFilter.setAuthenticationFailureHandler(authenticationFailureHandler());
        authFilter.setSecurityContextRepository(securityContextRepository());
        return authFilter;
    }

    @Bean
    protected CustomAuthenticationManager customAuthenticationManager() {
        return new CustomAuthenticationManager(userDetailsService(), passwordEncoder());
    }

    @Bean
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new CompositeSessionAuthenticationStrategy(
                Arrays.asList(
                        new ChangeSessionIdAuthenticationStrategy(),
                        new SessionFixationProtectionStrategy(),
                        new RegisterSessionAuthenticationStrategy(
                                sessionRegistry()
                        )
                )
        );
    }

    @Bean
    public UserSessionManager userSessionManager() {
        return new UserSessionManager(sessionRegistry(), securityContextRepository(), userDetailsService());
    }

    @Bean
    public LogoutHandler logoutHandler() {
        return new SecurityContextLogoutHandler();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    protected AuthenticationFailureHandler authenticationFailureHandler() {
        return new JsonAuthenticationFailureHandler();
    }

    @Bean
    protected AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new SimpleUrlAuthenticationSuccessHandler("/user");
    }

    @Bean
    protected AuthenticationEntryPoint jsonAuthenticationEntryPoint() {
        return new JsonAuthenticationEntryPoint();
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    protected PersistentUserDetailsService userDetailsService() {
        return new PersistentUserDetailsService(userRepository);
    }

    @Bean
    public SecurityContextRepository securityContextRepository() {
        return new HttpSessionSecurityContextRepository();
    }
}
