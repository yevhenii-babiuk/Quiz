package com.qucat.quiz.repositories.dao;

import com.qucat.quiz.repositories.entities.QuestionOption;

import java.util.List;

public interface QuestionOptionDao extends GenericDao<QuestionOption> {
    String TABLE_NAME = "question_option";
    List<QuestionOption> getByQuestionId(int id);
}
