package com.qucat.quiz.services;

import com.qucat.quiz.repositories.dao.UserDao;
import com.qucat.quiz.repositories.dto.game.UserDto;
import com.qucat.quiz.repositories.entities.*;
import com.qucat.quiz.repositories.entities.enums.Lang;
import com.qucat.quiz.repositories.entities.enums.MessageInfo;
import com.qucat.quiz.repositories.entities.enums.Role;
import com.qucat.quiz.repositories.entities.enums.TokenType;
import com.qucat.quiz.repositories.entities.enums.UserAccountStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
    private UserDao userDao;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private WebSocketSenderService webSocketSenderService;

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

        user.setImageId(imageService.addUserProfileImage());

        if (userByMail != null) {
            Token token = tokenService.getTokenByUserId(userByMail.getId());
            if (userByMail.getStatus() == UserAccountStatus.ACTIVATED) {
                return false;
            } else if (token != null && token.getExpiredDate().compareTo(new Date()) > 0) {
                return false;
            } else {
                id = userByMail.getId();
                user.setId(id);
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
        tokenService.saveToken(tokenForNewUser);
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
                .userId(user.getId())
                .build();
        tokenService.saveToken(token);
        emailSender.sendMessage(user.getMail(), user.getLogin(), URL + PASS_RECOVERY + token.getToken(), MessageInfo.passwordRecover.findByLang(Lang.EN));
        //todo get Lang
        return true;
    }

    public boolean openRegistrationToken(String tokenStr) {
        Token token = Token.builder()
                .token(tokenStr)
                .tokenType(TokenType.REGISTRATION)
                .build();
        int id = tokenService.getUserId(token);
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
        int id = tokenService.getUserId(token);
        return id != 0;
    }

    public boolean editPassword(String tokenStr, String password) {
        Token token = Token.builder()
                .token(tokenStr)
                .tokenType(TokenType.PASSWORD_RECOVERY)
                .build();
        int id = tokenService.getUserId(token);
        if (id == 0) {
            return false;
        }
        User user = userDao.get(id);
        user.setPassword(passwordEncoder.encode(password));
        userDao.update(user);
        return true;
    }

    public Page<User> getPageUserByRole(Role role, Optional<Integer> page, Optional<Integer> size) {
        if (role == null) {
            log.warn("Null id passed to find users by role");
            throw new IllegalArgumentException("Null id passed to find users by role");
        }
        return userDao.getUserByRole(role, PageRequest.of(page.orElse(0), size.orElse(10),
                Sort.Direction.DESC, "id"));
    }

    public Page<User> getAllUsersPage(Optional<Integer> page, Optional<Integer> size) {
        Page<User> allUsersPage = userDao.getAllUsersPage(
                PageRequest.of(page.orElse(0), size.orElse(10),
                        Sort.Direction.DESC, "id"));
        return allUsersPage;
    }

    public User getUserDataById(int id) {
        User user = userDao.get(id);

        //throw new NoSuchElementException("Such user not exist");

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
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            User user = userDao.getUserByLogin(username);
            boolean isActivated = user
                    .getStatus()
                    .equals(UserAccountStatus.ACTIVATED);
            if (!isActivated) {
                throw new DisabledException("User " + username + "has UNACTIVATED account status");
            }
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    public boolean markQuizAsFavorite(int userId, int quizId) {
        return userDao.markQuizAsFavorite(userId, quizId);
    }

    public void unmarkQuizAsFavorite(int userId, int quizId) {
        userDao.unmarkQuizAsFavorite(userId, quizId);
    }

    public boolean addUserFriend(int userId, int friendId) {
        boolean isAdded;
        isAdded = userDao.addUserFriend(userId, friendId);
        webSocketSenderService.sendNotification(friendId, userId, NotificationType.FRIEND_INVITATION);
        return isAdded;
    }

    public boolean deleteUserFriend(int userId, int friendId) {
        userDao.deleteUserFriend(userId, friendId);
        return true;
    }

    public List<User> getUserFriends(int userId) {
        return userDao.getUserFriends(userId);
    }

    public boolean checkUsersFriendship(int firstUserId, int secondUserId) {
        return userDao.checkUsersFriendship(firstUserId, secondUserId);
    }

    public Page<User> getUserFriendsPage(int userId, Optional<Integer> page, Optional<Integer> size) {
        Page<User> friendsPage = userDao.getUserFriendsPage(userId,
                PageRequest.of(page.orElse(0), size.orElse(10),
                        Sort.Direction.DESC, "id"));
        return friendsPage;
    }

    public List<FriendActivity> getAllFriendsActivity(int userId) {
        return userDao.getAllFriendsActivity(userId);
    }

    public Page<FriendActivity> getAllFriendsActivityPage(int userId, Optional<Integer> page, Optional<Integer> size) {
        Page<FriendActivity> friendsActivityPage = userDao.getAllFriendsActivityPage(userId,
                PageRequest.of(page.orElse(0), size.orElse(10),
                        Sort.Direction.DESC, "id"));
        return friendsActivityPage;
    }

    public List<FriendActivity> getFilteredFriendsActivity(int userId, boolean addFriend, boolean markQuizAsFavorite,
                                                           boolean publishQuiz, boolean achievement) {
        if (!addFriend && !markQuizAsFavorite && !publishQuiz && !achievement) {
            log.info("getFilteredFriendsActivity: Nothing to get");
            return null;
        }
        return userDao.getFilteredFriendsActivity(userId, addFriend, markQuizAsFavorite, publishQuiz, achievement);
    }

    public Page<FriendActivity> getFilteredFriendsActivityPage(int userId, boolean addFriend, boolean markQuizAsFavorite,
                                                               boolean publishQuiz, boolean achievement,
                                                               Optional<Integer> page, Optional<Integer> size) {
        if (!addFriend && !markQuizAsFavorite && !publishQuiz && !achievement) {
            log.info("getFilteredFriendsActivityPage: Nothing to get");
            return null;
        }
        Page<FriendActivity> friendsActivityPage = userDao.getFilteredFriendsActivityPage(
                userId, addFriend, markQuizAsFavorite, publishQuiz, achievement,
                PageRequest.of(page.orElse(0), size.orElse(10),
                        Sort.Direction.DESC, "id"));
        return friendsActivityPage;
    }

    public List<User> searchUsersByLogin(String login) {
        return userDao.searchUsersByLogin(login);
    }

    public Page<User> searchUsersByLogin(String login, Optional<Integer> page, Optional<Integer> size) {
        Page<User> users = userDao.searchUsersByLogin(login,
                PageRequest.of(page.orElse(0), size.orElse(10),
                        Sort.Direction.DESC, "id"));
        return users;
    }

    public List<User> searchUsersByLogin(String login, Role role) {
        return userDao.searchUsersByLogin(login, role);
    }

    public Page<User> searchUsersByLogin(String login, Role role, Optional<Integer> page, Optional<Integer> size) {
        Page<User> users = userDao.searchUsersByLogin(login, role,
                PageRequest.of(page.orElse(0), size.orElse(10),
                        Sort.Direction.DESC, "id"));
        return users;
    }


    public void updateUserImage(User user) {
        userDao.updateUserPhoto(user.getImageId(), user.getId());
    }

    public void updateUserStatus(int userId, UserAccountStatus status) {
        userDao.updateUserStatus(userId, status);
    }

    public void updateUsersScore(UserDto user) {
        User userFromDb = userDao.get(user.getRegisterId());
        userDao.updateUserScore(user.getRegisterId(),
                userFromDb.getScore() + user.getScore());
    }

    public boolean createUser(User user) {
        user.setStatus(UserAccountStatus.ACTIVATED);
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        user.setImageId(imageService.addUserProfileImage());
        int id = userDao.save(user);

        return id > 0;
    }
}
