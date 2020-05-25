package com.qucat.quiz.repositories.dao;

import com.qucat.quiz.repositories.entities.TakeQuiz;

import java.util.List;

public interface TakeQuizDao extends GenericDao<TakeQuiz> {

    String TABLE_NAME = "take_quiz";

    List<TakeQuiz> getUserCompletedQuizzes(int userId);

    TakeQuiz getUserResultByQuiz(int userId, int quizId);
}
