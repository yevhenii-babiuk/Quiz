package com.qucat.quiz.repositories.dao.implementation;

import com.qucat.quiz.repositories.dao.QuestionDao;
import com.qucat.quiz.repositories.dao.QuizDao;
import com.qucat.quiz.repositories.dao.mappers.QuizMapper;
import com.qucat.quiz.repositories.entities.Question;
import com.qucat.quiz.repositories.entities.Quiz;
import com.qucat.quiz.repositories.entities.QuizStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Map;

@Slf4j
@Repository
public class QuizDaoImpl extends GenericDaoImpl<Quiz> implements QuizDao {
    @Autowired
    private QuestionDao questionDao;

    @Value("#{${sql.quiz}}")
    private Map<String, String> quizQueries;

    protected QuizDaoImpl() {
        super(new QuizMapper(), TABLE_NAME);
    }

    @Override
    protected String getInsertQuery() {
        return quizQueries.get("insert");
    }

    @Override
    protected PreparedStatement getInsertPreparedStatement(PreparedStatement preparedStatement, Quiz quiz) throws SQLException {
        preparedStatement.setString(1, quiz.getName());
        preparedStatement.setInt(2, quiz.getAuthorId());
        preparedStatement.setInt(3, quiz.getCategoryId());
        preparedStatement.setString(4,
                quiz.getStatus() != null
                        ? quiz.getStatus().name().toLowerCase()
                        : QuizStatus.UNSAVED.name().toLowerCase());
        if (quiz.getPublishedDate() != null) {
            preparedStatement.setTimestamp(5, new Timestamp(quiz.getPublishedDate().getTime()));
        } else {
            preparedStatement.setNull(5, Types.TIMESTAMP);
        }
        if (quiz.getUpdatedDate() != null) {
            preparedStatement.setTimestamp(6, new Timestamp(quiz.getUpdatedDate().getTime()));
        } else {
            preparedStatement.setNull(6, Types.TIMESTAMP);
        }
        preparedStatement.setInt(7, quiz.getQuestionNumber());
        preparedStatement.setInt(8, quiz.getMaxScore());
        return preparedStatement;
    }

    @Override
    protected String getUpdateQuery() {
        return quizQueries.get("update");
    }

    @Override
    protected Object[] getUpdateParameters(Quiz quiz) {
        return new Object[]{quiz.getName(), quiz.getAuthorId(), quiz.getCategoryId(), quiz.getStatus().name().toLowerCase(),
                quiz.getPublishedDate(), quiz.getUpdatedDate(),
                quiz.getCreatedDate(), quiz.getQuestionNumber(), quiz.getMaxScore(), quiz.getId()};
    }

    @Override
    public Quiz getFullInformation(int id) {
        Quiz quiz = get(id);
        if (quiz != null) {
            quiz.setQuestions(questionDao.getByQuizId(id));
            for (Question q : quiz.getQuestions()) {
                questionDao.getFullInformation(q);
            }
        }
        return quiz;
    }

    @Override
    public Quiz getFullInformation(Quiz quiz) {
        if (quiz != null) {
            quiz.setQuestions(questionDao.getByQuizId(quiz.getId()));
            for (Question q : quiz.getQuestions()) {
                questionDao.getFullInformation(q);
            }
        }
        return quiz;    }
}
