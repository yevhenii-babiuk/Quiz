package com.qucat.quiz.services;

import com.qucat.quiz.repositories.dao.implementation.*;
import com.qucat.quiz.repositories.entities.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Slf4j
@Service
public class QuizService {
    @Autowired
    private QuizDaoImpl quizDao;

    @Autowired
    private QuestionDaoImpl questionDao;

    @Autowired
    private QuestionOptionDaoImpl questionOptionDao;

    @Autowired
    private ImageDaoImpl imageDao;

    @Autowired
    private TagDaoImpl tagDao;

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
            addQuestion(question);
        }

        addQuizTags(quiz);

        return true;
    }

    public int addImage(MultipartFile multipartFile) {
        if (multipartFile == null) {
            return -1;
        }

        byte[] fileBytes;
        try {
            fileBytes = multipartFile.getBytes();
        } catch (IOException e) {
            log.error("Error while get bytes of file", e);
            return -1;
        }

        String encodedFile = Base64.getEncoder().encodeToString(fileBytes);

        int imageId = imageDao.getIdBySrc(encodedFile);
        if (imageId != -1) {
            return imageId;
        }

        return imageDao.save(
                Image.builder()
                        .src(encodedFile)
                        .build()
        );
    }

    private int addQuestion(Question question) {
        int questionId = questionDao.save(question);
        if (questionId != -1) {
            for (QuestionOption option : question.getOptions()) {
                option.setQuestionId(questionId);
                questionOptionDao.save(option);
            }
        }
        return questionId;
    }

    private void addQuizTags(Quiz quiz) {
        for (Tag tag : quiz.getTags()) {
            if (!tag.getName().isEmpty()) {
                int tagId = tagDao.getIdByName(tag.getName());
                if (tagId == -1) {
                    tagDao.save(tag);
                }
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
            if (questionDao.get(question.getId()) != null) {
                questionDao.update(question);
                for (QuestionOption option : question.getOptions()) {
                    if (questionOptionDao.get(option.getId()) != null) {
                        questionOptionDao.update(option);
                    } else {
                        option.setQuestionId(question.getId());
                        questionOptionDao.save(option);
                    }
                }
            } else {
                question.setQuizId(quiz.getId());
                addQuestion(question);
            }
        }

        addQuizTags(quiz);
    }

}
