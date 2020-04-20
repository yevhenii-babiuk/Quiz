package com.qucat.quiz.repositories.dao;

import com.qucat.quiz.repositories.entities.Question;

import java.util.List;

public interface QuestionDao extends GenericDao<Question> {
    String TABLE_NAME = "question";

    List<Question> getByQuizId(int id);

    Question getFullInformation(int id);

    Question getFullInformation(Question question);

}
