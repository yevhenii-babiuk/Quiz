package com.qucat.quiz.repositories.dao;

import com.qucat.quiz.repositories.entities.*;

import java.util.List;

public interface DashboardDao {

    List<User> getTopUsers(int limit);

    User getBestUserByQuizId(int quizId);

    List<CategoryStatistics> getStatisticInTheCategory(int id);

    List<Statistics> getPercentOfCorrectAnswers(int id);

    BestQuiz getTheMostSuccessfulQuiz(int id);

    List<ComparedScores> getComparedScores(int userId);

    List<Statistics> getFriendsPreferences(int userId);

    List<Statistics> getStatisticOfQuizzesPlayed();

    List<AdminStatistics> getAmountOfCreatedAndPublishedQuizzes();
}
