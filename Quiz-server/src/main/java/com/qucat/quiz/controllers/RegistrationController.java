package com.qucat.quiz.controllers;

import com.qucat.quiz.repositories.entities.Role;
import com.qucat.quiz.repositories.entities.User;
import com.qucat.quiz.repositories.entities.UserAccountStatus;
import com.qucat.quiz.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("api/v1/registration")
public class RegistrationController {
    @Autowired
    private UserService userService;

    @PostMapping
    public boolean registerUser(@RequestBody User user) {
        user.setRole(Role.USER);
        user.setStatus(UserAccountStatus.UNACTIVATED);
        return userService.registerUser(user);
    }

    @GetMapping("{token}")
    public boolean confirmRegistration(@PathVariable String token) {
        return userService.openRegistrationToken(token);
    }
}