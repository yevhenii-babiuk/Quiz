package com.qucat.quiz.repositories.dao;

import com.qucat.quiz.repositories.entities.*;

import java.util.List;

public interface DashboardDao {

    List<User> getTopUsers(int limit);

    User getBestUserByQuizId(int quizId);

    List<CategoryStatistics> getStatisticInTheCategory(String login);

    List<Statistics> getPercentOfCorrectAnswers(String login);

    BestQuiz getTheMostSuccessfulQuiz(String login);

    List<ComparedScores> getComparedScores(String login);

    List<Statistics> getFriendsPreferences(int userId);

    List<Statistics> getStatisticOfQuizzesPlayed();

    List<AdminStatistics> getAmountOfCreatedAndPublishedQuizzes();
}
