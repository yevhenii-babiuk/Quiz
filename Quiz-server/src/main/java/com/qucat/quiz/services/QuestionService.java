package com.qucat.quiz.services;

import com.qucat.quiz.repositories.dao.QuestionDao;
import com.qucat.quiz.repositories.entities.Question;
import com.qucat.quiz.repositories.entities.QuestionOption;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class QuestionService {
    @Autowired
    private QuestionDao questionDao;

    @Autowired
    private QuestionOptionService questionOptionService;

    public int addQuestion(Question question) {
        int questionId = questionDao.save(question);
        if (questionId != -1) {
            for (QuestionOption option : question.getOptions()) {
                option.setQuestionId(questionId);
                questionOptionService.addQuestionOption(option);
            }
        }
        return questionId;
    }

    public void updateQuestion(Question question) {
        questionDao.update(question);
        for (QuestionOption option : question.getOptions()) {
            questionOptionService.updateQuestionOption(option);
        }
    }
}
