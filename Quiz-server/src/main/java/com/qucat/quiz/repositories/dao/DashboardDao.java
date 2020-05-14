package com.qucat.quiz.repositories.dao;

import com.qucat.quiz.repositories.entities.AdminStatistics;
import com.qucat.quiz.repositories.entities.BestQuiz;
import com.qucat.quiz.repositories.entities.CategoryStatistics;
import com.qucat.quiz.repositories.entities.ComparedScores;
import com.qucat.quiz.repositories.entities.QuizStatistics;
import com.qucat.quiz.repositories.entities.User;

import java.util.List;

public interface DashboardDao {

    List<User> getTopUsers(int limit);

    User getBestUserByQuizId(int quizId);

    List<CategoryStatistics> getStatisticInTheCategory(int id);

    List<QuizStatistics> getPercentOfCorrectAnswers(int id);

    BestQuiz getTheMostSuccessfulQuiz(int id);

    List<ComparedScores> getComparedScores(int userId);

    List<QuizStatistics> getFriendsPreferences(int userId);

    List<QuizStatistics> getStatisticOfQuizzesPlayed();

    List<AdminStatistics> getAmountOfCreatedAndPublishedQuizzes();
}
