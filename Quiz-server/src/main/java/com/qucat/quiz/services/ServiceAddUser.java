package com.qucat.quiz.services;

import com.qucat.quiz.repositories.entities.Lang;
import com.qucat.quiz.repositories.entities.Role;
import com.qucat.quiz.repositories.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class ServiceAddUser {

    @Autowired
    private EmailSender emailSender;


    public boolean addUser(User user) {
        int id = 5;//todo DAO add user
        String token = UUID.randomUUID().toString();
        //todo add token in DB
        emailSender.sendRegistrationMessage(user.getMail(), user.getLogin(), "http://localhost:8080/registration/"+token, Lang.UK);//todo get Lang, set url
        return true;
    }

    public boolean restorePassword(String mail){
        User user=new User("name", "sname", "login", mail, "pass", Role.USER);//todo get from DAO
        String token = UUID.randomUUID().toString();
        //todo add to DAO
        emailSender.sendResetPasswordMessage(user.getMail(), user.getLogin(),"http://localhost:8080/registration/"+token, Lang.UK);//todo get Lang, set url

        return true;
    }

    public boolean openedToken(String token){
        System.out.println(token);//todo
        return true;
    }

}

