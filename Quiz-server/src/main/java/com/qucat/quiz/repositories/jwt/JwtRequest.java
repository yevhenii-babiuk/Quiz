package com.qucat.quiz.repositories.jwt;

import com.qucat.quiz.repositories.entities.Role;

import java.io.Serializable;

public class JwtRequest implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;

//    private int id;
    private String username;
    private String password;
//    private Role role;

    //need default constructor for JSON Parsing
    public JwtRequest() {

    }

    public JwtRequest(String username, String password) {
//        this.setId(id);
        this.setUsername(username);
        this.setPassword(password);
//        this.setRole(role);
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}