package com.qucat.quiz.controllers;

import com.qucat.quiz.repositories.dao.implementation.UserDaoImpl;
import com.qucat.quiz.repositories.entities.Role;
import com.qucat.quiz.repositories.entities.TokenType;
import com.qucat.quiz.repositories.entities.User;
import com.qucat.quiz.repositories.entities.UserAccountStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Controller
public class NewController {

    @Autowired
    UserDaoImpl userDaoImpl;


    @GetMapping("/test5")
    public String home5() {
        System.out.println("hello");

        int id = 15;
        try {
            userDaoImpl.setPassword(id, "new password");
        } catch (RuntimeException e) {
            System.out.println("RuntimeException e" + e);
        }
        return "forward:/index.html";
    }


    @GetMapping("/test4")
    public String home4() {
        System.out.println("hello");

        int id = 15;
        try {
            userDaoImpl.setStatus(id, UserAccountStatus.ACTIVATED);

        } catch (RuntimeException e) {
            System.out.println("RuntimeException e" + e);
        }
        return "forward:/index.html";
    }


    @GetMapping("/test3")
    public String home3() {
        System.out.println("hello");

        String login = "login5";
        String password = "password";
        try {
            User user = userDaoImpl.getUser(login, password).get();
            System.out.println("user= " + user);

        } catch (RuntimeException e) {
            System.out.println("RuntimeException e" + e);
        }
        return "forward:/index.html";
    }

    @GetMapping("/test2")
    public String home2() {
        System.out.println("hello");

        String token = "7523381f-f11f-400c-bcb8-908e0550aba9";
        TokenType tokenType = TokenType.REGISTRATION;
        try {
            int id = userDaoImpl.getId(token, tokenType).get();
            System.out.println("id= " + id);

        } catch (RuntimeException e) {
            System.out.println("RuntimeException e" + e);
        }
        return "forward:/index.html";
    }

    @GetMapping("/test")
    public String home() {

        System.out.println("Hello");
        User user = User.builder()
                .firstName("name")
                .secondName("surname")
                .login("login7")
                .mail("mail7@gmail.com")
                .password("password")
                .profile("profile")
                .registrationDate(Date.valueOf("2020-04-12"))
                .role(Role.ADMIN)
                .status(UserAccountStatus.UNACTIVATED)
                .build();
        try {
            int id = userDaoImpl.save(user).orElse(-1);
            System.out.println("id= " + id);

            String token = UUID.randomUUID().toString();
            userDaoImpl.saveToken(token, TokenType.REGISTRATION, id);
        } catch (RuntimeException e) {
            System.out.println("RuntimeException e" + e);
        }
        return "forward:/index.html";
    }

    @GetMapping("/test0")
    public String home0() {
        List<User> users = userDaoImpl.getAll();
        System.out.println("users= " + users);
        return "forward:/index.html";
    }


}

