package com.qucat.quiz.repositories.dao;

import com.qucat.quiz.repositories.entities.User;

public interface UserDao extends Dao<User> {

    User getUserByLoginAndPassword(String login, String password);
}
