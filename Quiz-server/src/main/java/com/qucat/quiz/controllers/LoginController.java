package com.qucat.quiz.controllers;

import com.qucat.quiz.repositories.entities.User;
import com.qucat.quiz.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("api/v1/login")
public class LoginController {
    @Autowired
    private UserService userService;

    @PostMapping
    public boolean loginUser(@RequestBody User user) {
        //TODO: login user
        return true;
    }
}
