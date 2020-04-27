package com.qucat.quiz.services;

import com.qucat.quiz.repositories.dao.implementation.TokenDaoImpl;
import com.qucat.quiz.repositories.dao.implementation.UserDaoImpl;
import com.qucat.quiz.repositories.entities.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.NoSuchElementException;
import java.util.UUID;

@Slf4j
@Service
@PropertySource("classpath:mail/application-mail-config.properties")
public class UserService {

    private final String REGISTRATION = "registration/";

    private final String PASS_RECOVERY = "pass-recovery/";

    @Autowired
    private EmailSender emailSender;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDaoImpl userDao;

    @Autowired
    private TokenDaoImpl tokenDao;

    @Value("${url}")
    private String URL;


    @Transactional
    public boolean registerUser(User user) {

        if (userDao.getUserByLogin(user.getLogin()) != null) {
            return false;
        }

        int id;
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User userByMail = userDao.getUserByMail(user.getMail());

        if (userByMail != null) {
            Token token = tokenDao.get(userByMail.getUserId());
            if (userByMail.getStatus() == UserAccountStatus.ACTIVATED) {
                return false;
            } else if (token != null && token.getExpiredDate().compareTo(new Date()) > 0) {
                return false;
            } else {
                id = userByMail.getUserId();
                user.setUserId(id);
                userDao.update(user);
            }
        } else {
            id = userDao.save(user);
            if (id == -1) {
                return false;
            }
        }

        Token tokenForNewUser = Token.builder()
                .token(UUID.randomUUID().toString())
                .tokenType(TokenType.REGISTRATION)
                .userId(id)
                .build();
        tokenDao.save(tokenForNewUser);
        emailSender.sendMessage(user.getMail(), user.getLogin(), URL + REGISTRATION + tokenForNewUser.getToken(), MessageInfo.registration.findByLang(Lang.EN));
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

    public Page<User> getPageUserByRole(Role role, Pageable pageable) {
        if (role == null) {
            log.warn("Null id passed to find users by role");
            throw new IllegalArgumentException("Null id passed to find users by role");
        }

        return userDao.getUserByRole(role, pageable);
    }

    public User getUserDataByLogin(String login) {
        User user = userDao.getUserByLogin(login);

        if (user == null) {
            throw new NoSuchElementException("Such user not exist");
        }

        return user;
    }

    public void updateUserProfile(User user) {
        User currentUser = userDao.getUserByLogin(user.getLogin());

        currentUser.setFirstName(user.getFirstName());
        currentUser.setSecondName(user.getSecondName());
        currentUser.setPassword(passwordEncoder.encode(user.getPassword()));
        currentUser.setProfile(user.getProfile());
        currentUser.setMail(user.getMail());

        userDao.update(currentUser);
    }

    public void authenticate(String username, String password) throws Exception {
        //        Objects.requireNonNull(username);
        //        Objects.requireNonNull(password);

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    public boolean markQuizAsFavorite(int userId, int quizId) {
        return userDao.markQuizAsFavorite(userId, quizId);
    }
}