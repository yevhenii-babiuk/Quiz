package com.qucat.quiz.repositories.dao;

import com.qucat.quiz.repositories.entities.TakeQuiz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TakeQuizDao extends GenericDao<TakeQuiz> {

    String TABLE_NAME = "take_quiz";

    List<TakeQuiz> getUserCompletedQuizzes(int userId);

    Page<TakeQuiz> getPageUserCompletedQuiz(int userId, Pageable pageable);

    TakeQuiz getUserResultByQuiz(int userId, int quizId);
}
