package com.qucat.quiz.services;

import com.qucat.quiz.repositories.entities.Lang;
import com.qucat.quiz.repositories.entities.MessageInfo;
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
        //todo DAO add user
        String token = UUID.randomUUID().toString();
        //todo add token in DB
        emailSender.sendMessage(user.getMail(), user.getLogin(), "http://localhost:8080/registration/" + token, MessageInfo.passwordRecover.findByLang(Lang.EN));
        //todo get Lang, set url
        return true;
    }

    public boolean passwordRecovery(String mail) {
        User user = User.builder()
                .firstName("name")
                .secondName("sname")
                .login("login")
                .mail("mail")
                .password("pass")
                .role(Role.USER)
                .build();
        String token = UUID.randomUUID().toString();
        //todo add to DAO
        emailSender.sendMessage(user.getMail(), user.getLogin(), "http://localhost:8080/passwordRecovery/" + token, MessageInfo.passwordRecover.findByLang(Lang.EN));//todo get Lang, set url

        return true;
    }

    public boolean openRegisterToken(String token) {
        System.out.println(token);//todo
        return true;
    }

    public boolean openPasswordRecoveryToken(String token) {
        System.out.println(token);//todo
        return true;
    }

}

