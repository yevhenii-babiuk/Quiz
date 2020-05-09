package com.qucat.quiz.repositories.dao.mappers;

import com.qucat.quiz.repositories.entities.AchievementCharacteristic;
import com.qucat.quiz.repositories.entities.AchievementCondition;
import com.qucat.quiz.repositories.entities.ConditionOperator;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AchievementCharacteristicMapper implements RowMapper<AchievementCharacteristic> {
    @Override
    public AchievementCharacteristic mapRow(ResultSet resultSet, int i) throws SQLException {
        return AchievementCharacteristic.builder()
                .id(resultSet.getInt("id"))
                .script(resultSet.getString("script"))
                .name(resultSet.getString("name"))
                .build();
    }
}
