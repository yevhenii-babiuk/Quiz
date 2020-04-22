package com.qucat.quiz.repositories.dao;

import com.qucat.quiz.repositories.entities.Role;
import com.qucat.quiz.repositories.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserDao extends GenericDao<User> {

    String TABLE_NAME = "users";

    User getUserByLoginAndPassword(String login, String password);

    User getUserByMail(String mail);

    Page<User> getUserByRole(Role role, Pageable pageable);

    User getUserByLogin(String login);
}
