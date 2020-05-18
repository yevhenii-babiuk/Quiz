package com.qucat.quiz.repositories.dao;

import com.qucat.quiz.repositories.entities.TakeQuiz;

import java.util.List;

public interface TakeQuizDao {

    String TABLE_NAME = "take_quiz";

    List<TakeQuiz> getUserTakeQuizzes(int userId);

    List<TakeQuiz> getUserResultsByQuiz(int userId, int quizId);
}
