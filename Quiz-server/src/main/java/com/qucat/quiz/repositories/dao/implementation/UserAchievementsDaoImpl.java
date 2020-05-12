package com.qucat.quiz.repositories.dao.implementation;

import com.qucat.quiz.repositories.dao.UserAchievementsDao;
import com.qucat.quiz.repositories.dao.mappers.UserAchievementExtractor;
import com.qucat.quiz.repositories.dao.mappers.UserAchievementMapper;
import com.qucat.quiz.repositories.entities.Achievement;
import com.qucat.quiz.repositories.entities.User;
import com.qucat.quiz.repositories.entities.UserAchievement;
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
@PropertySource("classpath:achievement.properties")
public class UserAchievementsDaoImpl extends GenericDaoImpl<UserAchievement> implements UserAchievementsDao {

    @Value("#{${sql.userAchievements}}")
    private Map<String, String> userAchievementsQueries;

    protected UserAchievementsDaoImpl() {
        super(new UserAchievementMapper(), TABLE_NAME);
    }

    @Override
    protected String getInsertQuery() {
        return userAchievementsQueries.get("insert");
    }

    @Override
    protected PreparedStatement getInsertPreparedStatement(PreparedStatement preparedStatement, UserAchievement userAchievement) throws SQLException {
        preparedStatement.setInt(1, userAchievement.getUserId());
        preparedStatement.setInt(2, userAchievement.getAchievementId());
        return preparedStatement;
    }

    @Override
    protected String getUpdateQuery() {
        return null;
    }

    @Override
    protected Object[] getUpdateParameters(UserAchievement userAchievement) {
        return new Object[0];
    }

    @Override
    public List<Achievement> getAchievementsByUserId(int userId) {
        return jdbcTemplate.query(userAchievementsQueries.get("getAchievementsByUserId"),
                new Object[]{userId}, new UserAchievementExtractor());
    }

    @Override
    public List<Achievement> getAchievementsForAllUser() {
        return jdbcTemplate.query(userAchievementsQueries.get("getAchievementsForAll"),
                new UserAchievementExtractor());
    }


}
