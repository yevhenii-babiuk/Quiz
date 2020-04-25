package com.qucat.quiz.services;

import java.util.ArrayList;

import com.qucat.quiz.repositories.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDao uDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.qucat.quiz.repositories.entities.User user = uDao.getUserByLogin(username);
        if (user.getLogin().equals(username)) {
            return new User(user.getLogin(), user.getPassword(),
                    new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}