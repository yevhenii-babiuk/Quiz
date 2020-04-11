package com.qucat.quiz.controllers;

import com.qucat.quiz.repositories.entities.User;
import org.springframework.web.bind.annotation.*;

public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "forward:/index.html";
    }

    @PostMapping(value = "/login")
    @ResponseBody
    public boolean loginUser(@RequestBody User user) {
        //TODO: login user
        return true;
    }
}
