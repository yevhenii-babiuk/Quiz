package com.qucat.quiz.services;

import com.qucat.quiz.repositories.dao.implementation.*;
import com.qucat.quiz.repositories.entities.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class QuizService {
    @Autowired
    private QuizDaoImpl quizDao;

    @Autowired
    private TagService tagService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private QuestionService questionService;

    @Transactional
    public boolean createQuiz(Quiz quiz) {
        if (quiz == null) {
            return false;
        }

        int quizId = quizDao.save(quiz);
        if (quizId == -1) {
            return false;
        }

        for (Question question : quiz.getQuestions()) {
            question.setQuizId(quizId);
            questionService.addQuestion(question);
        }

        addQuizTags(quiz);

        return true;
    }

    private void addQuizTags(Quiz quiz) {
        for (Tag tag : quiz.getTags()) {
            int tagId = tagService.addTag(tag);
            if (tagId != -1) {
                quizDao.addTag(quiz.getId(), tagId);
            }
        }
    }

    @Transactional
    public void updateQuiz(Quiz quiz) {
        if (quiz == null) {
            return;
        }

        quizDao.update(quiz);
        for (Question question : quiz.getQuestions()) {
            questionService.updateQuestion(question);
        }

        addQuizTags(quiz);
    }

    public Quiz getQuizById(int id) {
        Quiz quiz = quizDao.getFullInformation(id);

        if (quiz != null) {
            quiz.setImage(imageService.getImageById(quiz.getImageId()));
            for (Question question : quiz.getQuestions()) {
                question.setImage(imageService.getImageById(question.getImageId()));
                for (QuestionOption option : question.getOptions()) {
                    option.setImage(imageService.getImageById(option.getImageId()));
                }
            }
        }

        return quiz;
    }
}
