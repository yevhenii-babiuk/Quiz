package com.qucat.quiz.services;

import com.qucat.quiz.repositories.dao.implementation.TokenDaoImpl;
import com.qucat.quiz.repositories.dao.implementation.UserDaoImpl;
import com.qucat.quiz.repositories.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;


@Service
@PropertySource("classpath:mail/application-mail-config.properties")
public class UserService {

    @Autowired
    private EmailSender emailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDaoImpl userDao;

    @Autowired
    private TokenDaoImpl tokenDao;

    @Value("${url}")
    private String URL;

    private final String REGISTRATION = "registration/";

    private final String PASS_RECOVERY = "pass-recovery/";

    @Transactional
    public boolean registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        int id = userDao.save(user);
        if (id == -1) {
            User userByMail = userDao.getUserByMail(user.getMail());
            if (userByMail == null || userByMail.getStatus() == UserAccountStatus.ACTIVATED) {
                return false;
            }

            Token token = tokenDao.get(userByMail.getUserId());
            if (token != null && token.getExpiredDate().compareTo(new Date()) > 0) {
                return false;
            }

            user.setUserId(userByMail.getUserId());
            userDao.update(user);
        }
        Token token = Token.builder()
                .token(UUID.randomUUID().toString())
                .tokenType(TokenType.REGISTRATION)
                .userId(id)
                .build();
        tokenDao.save(token);
        emailSender.sendMessage(user.getMail(), user.getLogin(), URL + REGISTRATION + token.getToken(), MessageInfo.registration.findByLang(Lang.EN));
        //todo get Lang
        return true;
    }

    public boolean passwordRecovery(String mail) {
        User user = userDao.getUserByMail(mail);
        if (user == null) {
            return false;
        }
        Token token = Token.builder()
                .token(UUID.randomUUID().toString())
                .tokenType(TokenType.PASSWORD_RECOVERY)
                .userId(user.getUserId())
                .build();
        tokenDao.save(token);
        emailSender.sendMessage(user.getMail(), user.getLogin(), URL + PASS_RECOVERY + token.getToken(), MessageInfo.passwordRecover.findByLang(Lang.EN));
        //todo get Lang
        return true;
    }

    public boolean openRegistrationToken(String tokenStr) {
        Token token = Token.builder()
                .token(tokenStr)
                .tokenType(TokenType.REGISTRATION)
                .build();
        int id = tokenDao.getUserId(token);
        if (id == 0) {
            return false;
        }
        User user = userDao.get(id);
        user.setStatus(UserAccountStatus.ACTIVATED);
        userDao.update(user);
        return true;
    }

    public User loginUser(String login, String password) {
        User user = userDao.getUserByLoginAndPassword(login, passwordEncoder.encode(password));
        if (user == null || user.getStatus() == UserAccountStatus.UNACTIVATED) {
            return null;
        }
        return user;
    }

    public boolean openPasswordRecoveryToken(String tokenStr) {
        Token token = Token.builder()
                .token(tokenStr)
                .tokenType(TokenType.PASSWORD_RECOVERY)
                .build();
        int id = tokenDao.getUserId(token);
        return id != 0;
    }

    public boolean editPassword(String tokenStr, String password) {
        Token token = Token.builder()
                .token(tokenStr)
                .tokenType(TokenType.PASSWORD_RECOVERY)
                .build();
        int id = tokenDao.getUserId(token);
        if (id == 0) {
            return false;
        }
        User user = userDao.get(id);
        user.setPassword(passwordEncoder.encode(password));
        return true;
    }

}