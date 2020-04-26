package com.qucat.quiz.repositories.dao.implementation;

import com.qucat.quiz.repositories.dao.DashboardDao;
import com.qucat.quiz.repositories.dao.mappers.UserMapper;
import com.qucat.quiz.repositories.entities.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
@PropertySource("classpath:dashboard.properties")
public class DashboardDaoImpl implements DashboardDao {
    @Value("#{${sql.dashboard}}")
    private Map<String, String> dashboardQueries;

    protected DashboardDaoImpl() {
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public List<User> getTopUsers(int limit) {
        return jdbcTemplate.query(dashboardQueries.get("getTopUsers"), new Object[]{limit}, new UserMapper());
    }

    @Override
    public User getBestUserByQuizId(int quizId) {
        User user;
        try {
            user = jdbcTemplate.queryForObject(dashboardQueries.get("getBestUserByQuizId"),
                    new Object[]{quizId}, new UserMapper()
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return user;
    }

    @Override
    public List<CategoryStatistics> getStatisticInTheCategory(String login) {
        return jdbcTemplate.query(dashboardQueries.get("getStatisticInTheCategoryByLogin"), new Object[]{login}, (rs, rowNum) ->
                new CategoryStatistics(
                        rs.getInt("category_id"),
                        rs.getString("name"),
                        rs.getTimestamp("take_date"),
                        rs.getInt("count")
                ));
    }

    @Override
    public Map<String, Double> getPercentOfCorrectAnswers(String login) {
        return jdbcTemplate.query(dashboardQueries.get("getPercentOfCorrectAnswersByLogin"), new Object[]{login}, (ResultSet rs) -> {
            HashMap<String, Double> results = new HashMap<>();
            while (rs.next()) {
                results.put(rs.getString("name"), rs.getDouble("correct_answers_persentage"));
            }
            return results;
        });
    }

    @Override
    public BestQuiz getTheMostSuccessfulQuiz(String login) {
        try {
            return jdbcTemplate.queryForObject(dashboardQueries.get("getMaxScoreByLogin"), new Object[]{login}, (rs, rowNum) ->
                    new BestQuiz(
                            rs.getString("name"),
                            rs.getTimestamp("take_date"),
                            rs.getInt("score")
                    ));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<ComparedScores> getComparedScores(String login) {
        return jdbcTemplate.query(dashboardQueries.get("getComparedScores"), new Object[]{login}, (rs, rowNum) ->
                new ComparedScores(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("score"),
                        rs.getString("login"),
                        rs.getInt("record")
                ));
    }

    @Override
    public Map<String, Integer> getFriendsPreferences(int userId) {
        return jdbcTemplate.query(dashboardQueries.get("getFriendsPreferences"), new Object[]{userId}, (ResultSet rs) -> {
            HashMap<String, Integer> results = new HashMap<>();
            while (rs.next()) {
                results.put(rs.getString("name"), rs.getInt("count"));
            }
            return results;
        });
    }

    @Override
    public Map<String, Integer> getStatisticOfQuizzesPlayed() {
        return jdbcTemplate.query(dashboardQueries.get("getStatisticOfQuizzesPlayed"), (ResultSet rs) -> {
            HashMap<String, Integer> results = new HashMap<>();
            while (rs.next()) {
                results.put(rs.getString("name"), rs.getInt("count"));
            }
            return results;
        });
    }

    @Override
    public Map<Timestamp, Integer> getAmountOfPublishedQuizzes() {
        return jdbcTemplate.query(dashboardQueries.get("getAmountOfPublishedQuizzes"), (ResultSet rs) -> {
            HashMap<Timestamp, Integer> results = new LinkedHashMap<>();
            while (rs.next()) {
                results.put(rs.getTimestamp("published_date"), rs.getInt("count"));
            }
            return results;
        });
    }
}

