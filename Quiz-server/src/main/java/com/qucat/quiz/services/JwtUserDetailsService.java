package com.qucat.quiz.services;

import java.util.ArrayList;
import com.qucat.quiz.repositories.dao.implementation.UserDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDaoImpl uDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

//        String password = "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6";
        //TODO replace getUserByLoginAndPassword with getUserByLogin
//        com.qucat.quiz.repositories.entities.User user = uDao.getUserByLoginAndPassword(username, password);
        if ("username".equals(username)) {
            return new User(username, "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6",
                    new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }

}