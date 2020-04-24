package com.qucat.quiz.controllers;

import com.qucat.quiz.repositories.entities.User;
import com.qucat.quiz.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/users")
public class UserController {
     @Autowired
     private UserService userService;

    @GetMapping
    public User getUser() {
        return userService.getUserDataByLogin("eugene");
    }

    @PostMapping
    public User updateUser(@RequestBody User editedUser) {
        userService.updateUserProfile(editedUser);
        return editedUser;
    }
}


