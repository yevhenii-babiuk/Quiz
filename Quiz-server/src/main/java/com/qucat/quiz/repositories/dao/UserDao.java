package com.qucat.quiz.repositories.dao;

import com.qucat.quiz.repositories.entities.FriendActivity;
import com.qucat.quiz.repositories.entities.Role;
import com.qucat.quiz.repositories.entities.User;
import com.qucat.quiz.repositories.entities.UserAccountStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserDao extends GenericDao<User> {

    String TABLE_NAME = "users";

    User getUserByLoginAndPassword(String login, String password);

    User getUserByMail(String mail);

    Page<User> getUserByRole(Role role, Pageable pageable);

    Page<User> getAllUsersPage(Pageable pageable);

    User getUserByLogin(String login);

    boolean markQuizAsFavorite(int userId, int quizId);

    void unmarkQuizAsFavorite(int userId, int quizId);

    boolean addUserFriend(int userId, int friendId);

    void deleteUserFriend(int userId, int friendId);

    List<User> getUserFriends(int userId);

    Page<User> getUserFriendsPage(int userId, Pageable pageable);

    List<FriendActivity> getAllFriendsActivity(int userId);

    Page<FriendActivity> getAllFriendsActivityPage(int userId, Pageable pageable);

    List<FriendActivity> getFilteredFriendsActivity(int userId,
                                                    boolean addFriend, boolean markQuizAsFavorite,
                                                    boolean publishQuiz, boolean achievement);

    Page<FriendActivity> getFilteredFriendsActivityPage(int userId,
                                                        boolean addFriend, boolean markQuizAsFavorite,
                                                        boolean publishQuiz, boolean achievement,
                                                        Pageable pageable);

    List<User> searchUsersByLogin(String login);

    List<User> searchUsersByLogin(String login, Role role);

    Page<User> searchUsersByLogin(String login, Pageable pageable);

    Page<User> searchUsersByLogin(String login, Role role, Pageable pageable);

    boolean checkUsersFriendship(int firstUserId, int secondUserId);

    void updateUserPhoto(int idImage, int userId);

    void updateUserStatus(int id, UserAccountStatus status);
}
