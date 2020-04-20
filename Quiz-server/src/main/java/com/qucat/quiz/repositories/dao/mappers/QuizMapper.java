package com.qucat.quiz.repositories.dao.mappers;

import com.qucat.quiz.repositories.entities.Quiz;
import com.qucat.quiz.repositories.entities.QuizStatus;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class QuizMapper implements RowMapper<Quiz> {

    @Override
    public Quiz mapRow(ResultSet resultSet, int i) throws SQLException {
        return Quiz.builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name"))
                .authorId(resultSet.getInt("author_id"))
                .categoryId(resultSet.getInt("category_id"))
                .status(QuizStatus.valueOf(resultSet.getString("status").toUpperCase()))
                .publishedDate(resultSet.getTimestamp("published_date"))
                .updatedDate(resultSet.getTimestamp("updated_date"))
                .createdDate(resultSet.getTimestamp("created_date"))
                .questionNumber(resultSet.getInt("questions_number"))
                .maxScore(resultSet.getInt("max_score"))
                .build();
    }
}