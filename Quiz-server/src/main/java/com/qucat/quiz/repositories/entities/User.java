package com.qucat.quiz.repositories.entities;

public class User {
    private String firstName;
    private String secondName;
    private String login;
    private String mail;
    private String password;
    private Role role;

    public User(String firstName, String secondName, String login, String mail, String password, Role role) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.login = login;
        this.mail = mail;
        this.password = password;
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getLogin() {
        return login;
    }

    public String getMail() {
        return mail;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }
}
