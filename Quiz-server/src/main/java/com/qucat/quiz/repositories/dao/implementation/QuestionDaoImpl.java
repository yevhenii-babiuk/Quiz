package com.qucat.quiz.repositories.dao.implementation;

import com.qucat.quiz.repositories.dao.QuestionDao;
import com.qucat.quiz.repositories.dao.QuestionOptionDao;
import com.qucat.quiz.repositories.dao.mappers.QuestionMapper;
import com.qucat.quiz.repositories.entities.Question;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class QuestionDaoImpl extends GenericDaoImpl<Question> implements QuestionDao {
    @Autowired
    private QuestionOptionDao questionOptionDao;

    @Value("#{${sql.question}}")
    private Map<String, String> questionQueries;

    protected QuestionDaoImpl() {
        super(new QuestionMapper(), TABLE_NAME);
    }


    @Override
    protected String getInsertQuery() {
        return questionQueries.get("insert");
    }

    @Override
    protected PreparedStatement getInsertPreparedStatement(PreparedStatement preparedStatement, Question question) throws SQLException {
        preparedStatement.setInt(1, question.getQuizId());
        preparedStatement.setString(2, question.getType().name().toLowerCase());
        preparedStatement.setString(3, question.getContent());
        preparedStatement.setInt(4, question.getScore());
        preparedStatement.setString(5, question.getImage());
        return preparedStatement;
    }

    @Override
    protected String getUpdateQuery() {
        return questionQueries.get("update");
    }

    @Override
    protected Object[] getUpdateParameters(Question question) {
        return new Object[]{question.getQuizId(), question.getType().name().toLowerCase(), question.getContent(),
                question.getScore(), question.getImage(), question.getId()};
    }

    @Override
    public List<Question> getByQuizId(int id) {
        return jdbcTemplate.query(questionQueries.get("getByQuizId"),
                new Object[]{id}, new QuestionMapper());
    }

    @Override
    public Question getFullInformation(int id) {
        Question question = get(id);
        if (question != null) {
            question.setOptions(questionOptionDao.getByQuestionId(id));
        }
        return question;
    }

    @Override
    public Question getFullInformation(Question question) {
        if (question != null) {
            question.setOptions(questionOptionDao.getByQuestionId(question.getId()));
        }
        return question;
    }
}
