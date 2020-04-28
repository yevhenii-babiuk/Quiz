package com.qucat.quiz.controllers;

import com.qucat.quiz.repositories.entities.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/dashboard")
public class DashboardController {

    @GetMapping("/top")
    public User[] getTopPlayers() {
        return List.of(User.builder().score(50).login("eugene").build(),
                User.builder().score(15).login("new").build()).toArray(User[]::new);

    }
}
