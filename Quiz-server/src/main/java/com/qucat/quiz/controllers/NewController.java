package com.qucat.quiz.controllers;

import com.qucat.quiz.repositories.dao.implementation.UserDaoImpl;
import com.qucat.quiz.repositories.entities.Role;
import com.qucat.quiz.repositories.entities.User;
import com.qucat.quiz.repositories.entities.UserAccountStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Date;

@Controller
public class NewController {

    @Autowired
    UserDaoImpl userDaoImpl;

    @GetMapping("/test")
    public String home() {

        System.out.println("Hello");
        User user = User.builder()
                .firstName("name")
                .secondName("surname")
                .login("login")
                .mail("mail")
                .password("password")
                .profile("profile")
                .registrationDate(Date.valueOf("2020-04-12"))
                .role(Role.ADMIN)
                .status(UserAccountStatus.UNACTIVATED)
                .build();
        userDaoImpl.save(user);

        return "forward:/index.html";
    }
}

