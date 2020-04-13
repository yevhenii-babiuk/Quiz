package com.qucat.quiz.services;

import com.qucat.quiz.repositories.entities.Lang;
import com.qucat.quiz.repositories.entities.Role;
import com.qucat.quiz.repositories.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class UserService {

    @Autowired
    private EmailSender emailSender;


    public boolean addUser(User user) {
        int id = 5;//todo DAO add user
        String token = UUID.randomUUID().toString();
        //todo add token in DB
        emailSender.sendRegistrationMessage(user.getMail(), user.getLogin(), "http://localhost:8080/registration/"+token, Lang.EN);//todo get Lang, set url
        return true;
    }

    public boolean passwordRecovery(String mail){
        User user=new User("name", "sname", "login", mail, "pass", Role.USER);//todo get from DAO
        String token = UUID.randomUUID().toString();
        //todo add to DAO
        emailSender.sendPasswordRecoveryMessage(user.getMail(), user.getLogin(),"http://localhost:8080/restore/"+token, Lang.EN);//todo get Lang, set url

        return true;
    }

    public boolean openRegisterToken(String token){
        System.out.println(token);//todo
        return true;
    }

    public boolean openPasswordRecoveryToken(String token){
        System.out.println(token);//todo
        return true;
    }

}

