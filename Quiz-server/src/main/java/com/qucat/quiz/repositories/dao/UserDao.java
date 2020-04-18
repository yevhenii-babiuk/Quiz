package com.qucat.quiz.repositories.dao;

import com.qucat.quiz.repositories.entities.User;

public interface UserDao extends GenericDao<User> {

    String TABLE_NAME = "users";

    User getUserByLoginAndPassword(String login, String password);

    User getUserByMail(String mail);
}
