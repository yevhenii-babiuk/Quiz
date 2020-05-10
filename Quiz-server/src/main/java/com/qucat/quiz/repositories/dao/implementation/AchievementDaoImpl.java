package com.qucat.quiz.repositories.dao.implementation;

import com.qucat.quiz.repositories.dao.AchievementDao;
import com.qucat.quiz.repositories.dao.mappers.AchievementExtractor;
import com.qucat.quiz.repositories.dao.mappers.AchievementMapper;
import com.qucat.quiz.repositories.entities.Achievement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;


@Slf4j
@Repository
@PropertySource("classpath:achievement.properties")
public class AchievementDaoImpl extends GenericDaoImpl<Achievement> implements AchievementDao {

    @Value("#{${sql.achievement}}")
    private Map<String, String> achievementQueries;


    @Override
    public List<Achievement> getAll() {
        return jdbcTemplate.query(
                achievementQueries.get("getAll"), new AchievementExtractor()
        );
    }

    protected AchievementDaoImpl() {
        super(new AchievementMapper(), TABLE_NAME);
    }

    @Override
    protected String getInsertQuery() {
        return achievementQueries.get("insert");
    }

    @Override
    protected PreparedStatement getInsertPreparedStatement(PreparedStatement preparedStatement, Achievement achievement) throws SQLException {
        preparedStatement.setString(1, achievement.getDescription());
        preparedStatement.setString(2, achievement.getName());
        return preparedStatement;
    }

    @Override
    protected String getUpdateQuery() {
        return achievementQueries.get("update");
    }

    @Override
    protected Object[] getUpdateParameters(Achievement achievement) {
        return new Object[]{achievement.getDescription(), achievement.getName(), achievement.getId()};
    }
}
