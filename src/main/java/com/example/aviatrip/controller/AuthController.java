package com.example.aviatrip.controller;

import com.example.aviatrip.config.springsecurity.UserSessionManager;
import com.example.aviatrip.model.entity.User;
import com.example.aviatrip.model.request.RepresentativeModel;
import com.example.aviatrip.model.request.auth.UpdateUserPasswordModel;
import com.example.aviatrip.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {


    private final UserService userService;
    private final LogoutHandler logoutHandler;
    private final UserSessionManager sessionManager;

    public AuthController(UserService userService, LogoutHandler logoutHandler, UserSessionManager sessionManager) {
        this.userService = userService;
        this.logoutHandler = logoutHandler;
        this.sessionManager = sessionManager;
    }

    @PostMapping("/signup/customer")
    public void signUpAsCustomer(@RequestBody @Valid User user, HttpServletRequest request, HttpServletResponse response) {
        sessionManager.assertNotAuthenticated();

        User persistedUser = userService.saveCustomer(user);

        sessionManager.rememberUser(persistedUser, request, response);
    }

    @PostMapping("/signup/representative")
    public void signUpAsRepresentative(@RequestBody @Valid RepresentativeModel model, HttpServletRequest request, HttpServletResponse response) {
        sessionManager.assertNotAuthenticated();

        User user = new User(model.getName(), model.getSurname(), model.getEmail(), model.getPassword());
        User persistedUser = userService.saveRepresentative(user, model.getCompanyName());

        sessionManager.rememberUser(persistedUser, request, response);
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        logoutHandler.logout(request, response, authentication);
    }

    @PatchMapping("/password")
    public void changePassword(@RequestBody @Valid UpdateUserPasswordModel data,
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
