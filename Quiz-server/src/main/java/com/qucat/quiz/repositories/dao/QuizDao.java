package com.qucat.quiz.repositories.dao;

import com.qucat.quiz.repositories.entities.Quiz;
import com.qucat.quiz.repositories.entities.QuizStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuizDao extends GenericDao<Quiz> {

    String TABLE_NAME = "quiz";

    Quiz getFullInformation(int id);

    Quiz getFullInformation(Quiz quiz);

    boolean addTag(int quizId, int tagId);

    Page<Quiz> getQuizByStatus(QuizStatus status, Pageable pageable);

    Page<Quiz> findAll(Pageable pageable);

    Page<Quiz> findAllByName(String name, Pageable pageable);
}
