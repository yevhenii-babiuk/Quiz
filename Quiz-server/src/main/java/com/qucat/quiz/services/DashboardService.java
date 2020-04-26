package com.qucat.quiz.services;

import com.qucat.quiz.repositories.dao.implementation.DashboardDaoImpl;
import com.qucat.quiz.repositories.entities.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class DashboardService {
    @Autowired
    private DashboardDaoImpl dashboardDao;

    public List<User> getTopUsers(int limit) {
        List<User> users = dashboardDao.getTopUsers(limit);
        if (users.isEmpty()) {
            log.warn("There are no users");
            return null;
        } else {
            return users;
        }
    }

    public User getBestUserInTheQuiz(int quizId) {
        User user = dashboardDao.getBestUserByQuizId(quizId);
        if (user == null) {
            log.error("Such user doesn`t exist");
            return null;
        } else {
            return user;
        }
    }

    public List<CategoryStatistics> getStatisticInTheCategory(String login) {
        List<CategoryStatistics> categoryStatistics = dashboardDao.getStatisticInTheCategory(login);
        if (categoryStatistics.isEmpty()) {
            log.warn("Statistic is empty");
            return null;
        } else {
            return categoryStatistics;
        }
    }

    public Map<String,Double> getPercentOfCorrectAnswers(String login) {
        Map<String,Double> correctAnswers = dashboardDao.getPercentOfCorrectAnswers(login);
        if (correctAnswers.isEmpty()) {
            log.warn("Can`t get information about correct answers");
            return null;
        } else {
            return correctAnswers;
        }
    }

    public BestQuiz getTheMostSuccessQuiz(String login) {
        BestQuiz bestQuiz = dashboardDao.getTheMostSuccessfulQuiz(login);
        if (bestQuiz == null) {
            log.error("Such user doesn`t exist");
            return null;
        } else {
            return bestQuiz;
        }
    }

    public List<ComparedScores> getComparedScores(String login) {
        List<ComparedScores> comparedScores = dashboardDao.getComparedScores(login);
        if (comparedScores.isEmpty()) {
            log.warn("Can`t get information about scores");
            return null;
        } else {
            return comparedScores;
        }
    }

    public Map<String,Integer> getFriendsPreferences(int userId) {
        Map<String,Integer>  friendsPreferences = dashboardDao.getFriendsPreferences(userId);
        if (friendsPreferences.isEmpty()) {
            log.warn("Can`t get information about friends` preferences");
            return null;
        } else {
            return friendsPreferences;
        }
    }

    public Map<String,Integer> getStatisticOfQuizzesPlayed() {
        Map<String,Integer>  playedQuizzes = dashboardDao.getStatisticOfQuizzesPlayed();
        if (playedQuizzes.isEmpty()) {
            log.warn("There are no quizzes played");
            return null;
        } else {
            return playedQuizzes;
        }
    }

    public Map<Timestamp,Integer> getAmountOfPublishedQuizzes() {
        Map<Timestamp,Integer>  publishedQuizzes = dashboardDao.getAmountOfPublishedQuizzes();
        if (publishedQuizzes.isEmpty()) {
            log.warn("There are no published quizzes");
            return null;
        } else {
            return publishedQuizzes;
        }
    }
}
