package com.qucat.quiz.repositories.dao;

import com.qucat.quiz.repositories.entities.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public interface DashboardDao {

    List<User> getTopUsers(int limit);

    User getBestUserByQuizId(int quizId);

    List<CategoryStatistics> getStatisticInTheCategory(String login);

    Map<String,Double> getPercentOfCorrectAnswers(String login);

    BestQuiz getTheMostSuccessfulQuiz(String login);

    List<ComparedScores> getComparedScores(String login);

    Map<String,Integer> getFriendsPreferences(int userId);

    Map<String,Integer> getStatisticOfQuizzesPlayed();

    Map<Timestamp,Integer> getAmountOfPublishedQuizzes();
}
