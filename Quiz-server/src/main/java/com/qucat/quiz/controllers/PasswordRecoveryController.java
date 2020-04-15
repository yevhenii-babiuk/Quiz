package com.qucat.quiz.controllers;

import com.qucat.quiz.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("api/v1/pass-recovery")
public class PasswordRecoveryController {
    @Autowired
    private UserService userService;

    @PostMapping
    public boolean recoveryPass(@RequestBody String email) {
        return userService.passwordRecovery(email);
    }

    @GetMapping("{token}")
    public boolean openByToken(@PathVariable String token) {
        return userService.openPasswordRecoveryToken(token);
    }

    @PutMapping("{token}")
    public boolean createNewPass(@PathVariable String token, @RequestBody String password) {
        //todo new pass
        return true;
    }
}
