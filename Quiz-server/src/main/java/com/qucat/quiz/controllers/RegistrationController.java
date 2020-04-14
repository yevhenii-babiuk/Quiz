package com.qucat.quiz.controllers;

import com.qucat.quiz.repositories.entities.User;
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
        //TODO: user.setRole
        return userService.addUser(user);
    }

    @GetMapping("{token}")
    public boolean confirmRegistration(@PathVariable String token) {
        return userService.openRegisterToken(token);
    }
}