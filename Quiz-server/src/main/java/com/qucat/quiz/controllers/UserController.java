package com.qucat.quiz.controllers;

import com.qucat.quiz.repositories.entities.User;
import com.qucat.quiz.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("{id}")
    public User getUser(@PathVariable int id) {
        return userService.getUserDataById(id);
    }

    @PutMapping
    public void updateUser(@RequestBody User editedUser) {
        userService.updateUserProfile(editedUser);
    }

    @GetMapping
    public List<User> getAnnouncements(@RequestParam(value = "pageNumber") double pageNumber,
                                       @RequestParam(value = "allUsers") boolean isPublished) {

        return userService.getAll();
    }
}
