package com.qucat.quiz.repositories.dao;

import com.qucat.quiz.repositories.entities.Quiz;
import com.qucat.quiz.repositories.entities.QuizStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Timestamp;
import java.util.List;

public interface QuizDao extends GenericDao<Quiz> {

    String TABLE_NAME = "quiz";

    List<Quiz> getAllFullInfo();

    Quiz getFullInfo(int id);

    boolean addTag(int quizId, int tagId);

    void removeTag(int quizId, int tagId);

    Page<Quiz> getQuizByStatus(QuizStatus status, Pageable pageable);

    Page<Quiz> findAllForPage(Pageable pageable, String name, String author,
                              List<String> category, Timestamp minDate, Timestamp maxDate, List<String> tags);
}
