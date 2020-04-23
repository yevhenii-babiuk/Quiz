package com.qucat.quiz.repositories.dao.implementation;

import com.qucat.quiz.repositories.dao.*;
import com.qucat.quiz.repositories.dao.mappers.QuizExtractor;
import com.qucat.quiz.repositories.dao.mappers.QuizMapper;

import com.qucat.quiz.repositories.entities.Quiz;
import com.qucat.quiz.repositories.entities.QuizStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
@PropertySource("classpath:quiz.properties")
public class QuizDaoImpl extends GenericDaoImpl<Quiz> implements QuizDao {

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
        preparedStatement.setInt(9, quiz.getImageId());
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
                quiz.getCreatedDate(), quiz.getQuestionNumber(), quiz.getMaxScore(), quiz.getImageId(), quiz.getId()};
    }


    @Override
    public List<Quiz> getAllFullInfo() {
        return jdbcTemplate.query(quizQueries.get("getFullInfo"), new QuizExtractor());
    }

    @Override
    public Quiz getFullInfo(int id) {
        String getQuery = quizQueries.get("getFullInfo").replace(";", " WHERE quiz.id = ?;");
        List<Quiz> result = jdbcTemplate.query(getQuery, new Object[]{id}, new QuizExtractor());
        return result.size() == 0 ? null : result.get(0);
    }

    @Override
    public boolean addTag(int quizId, int tagId) {
        try {
            jdbcTemplate.update(
                    quizQueries.get("addTag"),
                    quizId, tagId
            );
        } catch (DuplicateKeyException e) {
            return false;
        }
        return true;
    }

    @Override
    public Page<Quiz> getQuizByStatus(QuizStatus status, Pageable pageable) {
        int rowTotal = jdbcTemplate.queryForObject(quizQueries.get("rowCountByStatus"),
                new Object[]{status.name().toLowerCase()}, Integer.class);
        List<Quiz> quizzes = jdbcTemplate.query(quizQueries.get("getPageByStatus"),
                new QuizMapper());
        return new PageImpl<>(quizzes, pageable, rowTotal);
    }

    @Override
    public Page<Quiz> findAllForPage(Pageable pageable) {
        int total = jdbcTemplate.queryForObject(quizQueries.get("rowCount"),
                new Object[]{},
                (resultSet, number) -> resultSet.getInt(1));
        List<Quiz> quizzes = jdbcTemplate.query(quizQueries.get("getFullInfo").replace(";", " LIMIT ? OFFSET ?;"),
                new Object[]{ pageable.getPageSize(), pageable.getOffset()},
                new QuizExtractor());
        return new PageImpl<>(quizzes, pageable, total);
    }

    @Override
    public Page<Quiz> findAllByName(String name, Pageable pageable) {
        int total = jdbcTemplate.queryForObject(quizQueries.get("nameRowCount"),
                new Object[]{},
                (resultSet, number) -> resultSet.getInt(1));

        List<Quiz> quizzes = jdbcTemplate.query(quizQueries.get("getPageByName"),
                new Object[]{name, pageable.getPageSize(), pageable.getOffset()},
                new QuizMapper());
        return new PageImpl<>(quizzes, pageable, total);
    }
}
