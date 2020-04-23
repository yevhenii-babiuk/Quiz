package com.qucat.quiz.controllers;

import com.qucat.quiz.repositories.entities.User;
import com.qucat.quiz.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/edit")
public class EditController {
    @Autowired
    private UserService userService;

    @PostMapping
    public User setEditedUser(@RequestBody User editedUser) {
        userService.updateUserProfile(editedUser);
        return editedUser;
    }
}


