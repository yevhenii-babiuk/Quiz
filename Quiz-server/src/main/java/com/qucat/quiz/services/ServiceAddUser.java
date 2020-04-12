package com.qucat.quiz.services;

import com.qucat.quiz.repositories.entities.Lang;
import com.qucat.quiz.repositories.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;


@Service
public class ServiceAddUser {

    @Autowired
    private EmailSender emailSender;

    private SecureRandom secureRandom = new SecureRandom();
    private String getToken(int id) {

        byte[] token = new byte[20];
        secureRandom.nextBytes(token);
        return Base64.getUrlEncoder().withoutPadding().encodeToString((Arrays.toString(token) + id).getBytes()); //base64 encoding

    }

    public void addUser(User user) {
        int id = 5;//todo DAO add user
        String token = getToken(id);
        //todo add token in DB
        emailSender.sendRegistrationMessage(user.getMail(), user.getLogin(), "http://localhost:8080/registration/"+token, Lang.UK);//todo get Lang, set url
    }

}

