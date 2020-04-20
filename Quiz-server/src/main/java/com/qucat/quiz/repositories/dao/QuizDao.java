package com.qucat.quiz.repositories.dao;

import com.qucat.quiz.repositories.entities.Quiz;

public interface QuizDao extends GenericDao<Quiz> {

    String TABLE_NAME = "quiz";

    Quiz getFullInformation(int id);

    Quiz getFullInformation(Quiz quiz);
}
