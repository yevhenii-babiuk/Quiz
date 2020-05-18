package com.qucat.quiz.repositories.dao.implementation;

import com.qucat.quiz.repositories.dao.TakeQuizDao;
import com.qucat.quiz.repositories.dao.mappers.TakeQuizMapper;
import com.qucat.quiz.repositories.entities.TakeQuiz;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
    public List<TakeQuiz> getUserCompletedQuizzes(int userId) {
        return jdbcTemplate.query(
                queries.get("getAllInfo"),
                new Object[]{userId},
                new TakeQuizMapper()
        );
    }

    @Override
    public Page<TakeQuiz> getPageUserCompletedQuiz(int userId, Pageable pageable) {
        int total = jdbcTemplate.queryForObject(queries.get("rowCount"),
                new Object[]{userId},
                (resultSet, number) -> resultSet.getInt("row_count"));

        List<TakeQuiz> takeQuizzes = jdbcTemplate.query(
                queries.get("getAllInfo").replace(";", " LIMIT ? OFFSET ?;"),
                new Object[]{userId, pageable.getPageSize(), pageable.getOffset()},
                new TakeQuizMapper());
        return new PageImpl<>(takeQuizzes, pageable, total);
    }

    @Override
    public TakeQuiz getUserResultByQuiz(int userId, int quizId) {
        TakeQuiz takeQuiz;
        try {
            takeQuiz = jdbcTemplate.queryForObject(queries.get("getQuizResultByUser"),
                    new Object[]{userId, quizId}, new TakeQuizMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return takeQuiz;
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
