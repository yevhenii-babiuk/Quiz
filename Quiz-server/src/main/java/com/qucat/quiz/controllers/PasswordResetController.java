package com.qucat.quiz.controllers;

import com.qucat.quiz.repositories.entities.User;
import com.qucat.quiz.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("api/v1/pass-reset")
public class PasswordResetController {
    @Autowired
    private UserService userService;

    @PostMapping
    public boolean resetPass(@RequestBody String email) {
        return userService.restorePassword(email);
    }

    @GetMapping("{token}")
    public boolean openByToken(@PathVariable String token) {
        return userService.openRestorePasswordToken(token);
    }

    @PutMapping("{token}")
    public boolean createNewPass(@PathVariable String token, @RequestBody String password) {
        userService.openRestorePasswordToken(token);
        //TODO: new pass
        return true;
    }
}
