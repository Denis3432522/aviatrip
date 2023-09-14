package com.example.aviatrip.controller;

import com.example.aviatrip.config.requestmodel.UpdateUserEmailModel;
import com.example.aviatrip.config.requestmodel.UpdateUserNameModel;
import com.example.aviatrip.config.requestmodel.UpdateUserSurnameModel;
import com.example.aviatrip.model.User;
import com.example.aviatrip.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user")
public class UserController {

    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public User user(@AuthenticationPrincipal Long userId) {
        return userService.retrieveUser(userId);
    }

    @PatchMapping("/name")
    public void changeName(@RequestBody @Valid UpdateUserNameModel model, @AuthenticationPrincipal Long userId) {
        userService.updateName(model.name(), userId);
    }

    @PatchMapping("/surname")
    public void changeSurname(@RequestBody @Valid UpdateUserSurnameModel model, @AuthenticationPrincipal Long userId) {
        userService.updateSurname(model.surname(), userId);
    }

    @PatchMapping("/email")
    public void changeEmail(@RequestBody @Valid UpdateUserEmailModel model, @AuthenticationPrincipal Long userId) {
        userService.updateEmail(model.email(), userId);
    }


}
