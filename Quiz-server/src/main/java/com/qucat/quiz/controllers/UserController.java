package com.qucat.quiz.controllers;

import com.qucat.quiz.repositories.entities.User;
import com.qucat.quiz.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public User getUser() {
        try {
            return userService.getUserDataByLogin("eugene");
        } catch (NoSuchElementException e) {
            log.error("User doesn`t exist");
            return null;
        }
    }

    @PostMapping
    public User updateUser(@RequestBody User editedUser) {
        userService.updateUserProfile(editedUser);
        return editedUser;
    }
}


