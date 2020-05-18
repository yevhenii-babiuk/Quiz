package com.qucat.quiz.repositories.dao.implementation;

import com.qucat.quiz.repositories.dao.TakeQuizDao;
import com.qucat.quiz.repositories.dao.mappers.TakeQuizMapper;
import com.qucat.quiz.repositories.entities.TakeQuiz;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
@PropertySource("classpath:takeQuiz.properties")
public class TakeQuizDaoImpl extends GenericDaoImpl<TakeQuiz> implements TakeQuizDao {


    @Value("#{${sql.takeQuiz}}")
    private Map<String, String> queries;

    protected TakeQuizDaoImpl() {
        super(new TakeQuizMapper(), TABLE_NAME);
    }


    @Override
    public List<TakeQuiz> getUserTakeQuizzes(int userId) {
        return null;
    }

    @Override
    public List<TakeQuiz> getUserResultsByQuiz(int userId, int quizId) {
        return null;
    }

    @Override
    protected String getInsertQuery() {
        return queries.get("insert");
    }

    @Override
    protected PreparedStatement getInsertPreparedStatement(PreparedStatement preparedStatement, TakeQuiz takeQuiz) throws SQLException {
        preparedStatement.setInt(1, takeQuiz.getUserId());
        preparedStatement.setInt(2, takeQuiz.getQuizId());
        preparedStatement.setBoolean(3, takeQuiz.isCompleted());
        preparedStatement.setInt(4, takeQuiz.getScore());

        return preparedStatement;
    }

    @Override
    protected String getUpdateQuery() {
        return queries.get("update");
    }

    @Override
    protected Object[] getUpdateParameters(TakeQuiz takeQuiz) {
        return new Object[]{
                takeQuiz.getScore(), takeQuiz.getUserId(), takeQuiz.getQuizId()
        };
    }
}
