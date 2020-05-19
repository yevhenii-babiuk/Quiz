package com.qucat.quiz.controllers;

import com.qucat.quiz.repositories.entities.enums.Role;
import com.qucat.quiz.repositories.entities.User;
import com.qucat.quiz.repositories.entities.enums.UserAccountStatus;
import com.qucat.quiz.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/registration")
public class RegistrationController {
    @Autowired
    private UserService userService;

    @PostMapping
    public boolean registerUser(@RequestBody @Valid User user) {
        return userService.registerUser(user);
    }

    @GetMapping("{token}")
    public boolean checkRegistrationTokenExistence(@PathVariable String token) {
        boolean tokenValid = userService.openRegistrationToken(token);
        return tokenValid;
    }
}