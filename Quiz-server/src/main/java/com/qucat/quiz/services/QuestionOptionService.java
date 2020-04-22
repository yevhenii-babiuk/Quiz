package com.qucat.quiz.services;

import com.qucat.quiz.repositories.dao.implementation.QuestionOptionDaoImpl;
import com.qucat.quiz.repositories.entities.QuestionOption;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class QuestionOptionService {
    @Autowired
    private QuestionOptionDaoImpl questionOptionDao;

    public int addQuestionOption(QuestionOption questionOption) {
        return questionOptionDao.save(questionOption);
    }

    public void updateQuestionOption(QuestionOption questionOption) {
        questionOptionDao.update(questionOption);
    }
}
