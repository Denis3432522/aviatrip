package com.example.aviatrip.controller;

import com.example.aviatrip.config.exception.UserAlreadyAuthenticatedException;
import com.example.aviatrip.config.requestmodel.UpdatePasswordModel;
import com.example.aviatrip.config.springsecurity.UserSessionManager;
import com.example.aviatrip.model.User;
import com.example.aviatrip.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final LogoutHandler logoutHandler;
    private final UserSessionManager sessionManager;

    public AuthController(UserService userService, LogoutHandler logoutHandler, UserSessionManager sessionManager) {
        this.userService = userService;
        this.logoutHandler = logoutHandler;
        this.sessionManager = sessionManager;
    }

    @PostMapping("/signup")
    @Transactional
    public void signUp(@RequestBody @Valid User user, HttpServletRequest request, HttpServletResponse response) {
        if(sessionManager.isUserAuthenticated())
            throw new UserAlreadyAuthenticatedException("already authenticated");

        userService.createUser(user);
        sessionManager.rememberUser(user, request, response);
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        logoutHandler.logout(request, response, authentication);
    }

    @PatchMapping("/password")
    public void changePassword(@RequestBody @Valid UpdatePasswordModel data,
                               Authentication auth, HttpServletRequest request, HttpServletResponse response) {

        userService.updatePassword(data.password(), (Long) auth.getPrincipal());
        logoutHandler.logout(request, response, auth);
    }

    @DeleteMapping("/user")
    public void deleteUser(Authentication auth, HttpServletRequest request, HttpServletResponse response) {
        logoutHandler.logout(request, response, auth);
        userService.deleteUser((Long) auth.getPrincipal());
    }
}
