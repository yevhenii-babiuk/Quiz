package com.qucat.quiz.services;

import com.qucat.quiz.repositories.dao.implementation.DashboardDaoImpl;
import com.qucat.quiz.repositories.entities.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class DashboardService {
    @Autowired
    private DashboardDaoImpl dashboardDao;

    public List<User> getTopUsers(int limit) {
        List<User> users = dashboardDao.getTopUsers(limit);
        if (users.isEmpty()) {
            log.warn("There are no users");
            return Collections.emptyList();
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

    public List<CategoryStatistics> getStatisticInTheCategory(int id) {
        List<CategoryStatistics> categoryStatistics = dashboardDao.getStatisticInTheCategory(id);
        if (categoryStatistics.isEmpty()) {
            log.warn("Statistic is empty");
            return Collections.emptyList();
        } else {
            return categoryStatistics;
        }
    }

    public List<Statistics> getPercentOfCorrectAnswers(int id) {
        List<Statistics> correctAnswers = dashboardDao.getPercentOfCorrectAnswers(id);
        if (correctAnswers.isEmpty()) {
            log.warn("Can`t get information about correct answers");
            return Collections.emptyList();
        } else {
            return correctAnswers;
        }
    }

    public BestQuiz getTheMostSuccessQuiz(int id) {
        BestQuiz bestQuiz = dashboardDao.getTheMostSuccessfulQuiz(id);
        if (bestQuiz == null) {
            log.error("Such user doesn`t exist");
            return null;
        } else {
            return bestQuiz;
        }
    }

    public List<ComparedScores> getComparedScores(int userId) {
        List<ComparedScores> comparedScores = dashboardDao.getComparedScores(userId);
        if (comparedScores.isEmpty()) {
            log.warn("Can`t get information about scores");
            return Collections.emptyList();
        } else {
            return comparedScores;
        }
    }

    public List<Statistics> getFriendsPreferences(int userId) {
        List<Statistics> friendsPreferences = dashboardDao.getFriendsPreferences(userId);
        if (friendsPreferences.isEmpty()) {
            log.warn("Can`t get information about friends` preferences");
            return Collections.emptyList();
        } else {
            return friendsPreferences;
        }
    }

    public List<Statistics> getStatisticOfQuizzesPlayed() {
        List<Statistics> playedQuizzes = dashboardDao.getStatisticOfQuizzesPlayed();
        if (playedQuizzes.isEmpty()) {
            log.warn("There are no quizzes played");
            return Collections.emptyList();
        } else {
            return playedQuizzes;
        }
    }

    public List<AdminStatistics> getAmountOfCreatedAndPublishedQuizzes() {
        List<AdminStatistics> adminStatistics = dashboardDao.getAmountOfCreatedAndPublishedQuizzes();
        if (adminStatistics.isEmpty()) {
            log.warn("There are no created or published quizzes");
            return Collections.emptyList();
        } else {
            return adminStatistics;
        }
    }
}
