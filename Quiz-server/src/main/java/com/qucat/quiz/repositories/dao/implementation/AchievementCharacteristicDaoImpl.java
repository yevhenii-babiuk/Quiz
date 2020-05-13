package com.qucat.quiz.repositories.dao.implementation;

import com.qucat.quiz.repositories.dao.AchievementCharacteristicDao;
import com.qucat.quiz.repositories.dao.mappers.AchievementCharacteristicMapper;
import com.qucat.quiz.repositories.entities.AchievementCharacteristic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

@Slf4j
@Repository
@PropertySource("classpath:achievement.properties")
public class AchievementCharacteristicDaoImpl extends GenericDaoImpl<AchievementCharacteristic> implements AchievementCharacteristicDao {

    @Value("#{${sql.achievementCharacteristic}}")
    private Map<String, String> achievementConditionQueries;

    protected AchievementCharacteristicDaoImpl() {
        super(new AchievementCharacteristicMapper(), TABLE_NAME);
    }

    @Override
    protected String getInsertQuery() {
        return achievementConditionQueries.get("insert");
    }

    @Override
    protected PreparedStatement getInsertPreparedStatement(PreparedStatement preparedStatement, AchievementCharacteristic achievementCharacteristic) throws SQLException {
        preparedStatement.setString(1, achievementCharacteristic.getName());
        preparedStatement.setString(2, achievementCharacteristic.getScript());
        return preparedStatement;
    }

    @Override
    protected String getUpdateQuery() {
        return achievementConditionQueries.get("update");
    }

    @Override
    protected Object[] getUpdateParameters(AchievementCharacteristic achievementCharacteristic) {
        return new Object[]{achievementCharacteristic.getName(), achievementCharacteristic.getScript(), achievementCharacteristic.getId()};
    }
}
