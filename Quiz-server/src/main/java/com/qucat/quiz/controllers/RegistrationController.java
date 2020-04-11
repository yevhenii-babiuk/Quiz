package com.qucat.quiz.controllers;

import com.qucat.quiz.repositories.entities.Role;
import com.qucat.quiz.repositories.entities.User;
import com.qucat.quiz.services.ServiceAddUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegistrationController {

    @Autowired
    private ServiceAddUser addUserService;

    @GetMapping("/registration")
    public String registration() {
        return "forward:/index.html";
    }

    @PostMapping(value = "/registration")
    @ResponseBody
    public boolean registerUser(@RequestBody User user) {
        //TODO: user.setRole
        addUserService.addUser(user);
        return true;
    }

    @GetMapping("/registration/{token}")
    public String confirmRegistration(@PathVariable String token) {
        //TODO: confirm registration
        return "forward:/index.html";
    }
}