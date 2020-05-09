package com.qucat.quiz.repositories.dao.mappers;

import com.qucat.quiz.repositories.entities.ConditionOperator;
import org.springframework.jdbc.core.RowMapper;
import com.qucat.quiz.repositories.entities.AchievementCondition;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AchievementConditionMapper implements RowMapper<AchievementCondition> {
    @Override
    public AchievementCondition mapRow(ResultSet resultSet, int i) throws SQLException {
        return AchievementCondition.builder()
                .id(resultSet.getInt("id"))
                .operator(ConditionOperator.valueOf(resultSet.getString("operator")))
                .value(resultSet.getInt("value"))
                .achievementId(resultSet.getInt("achievement_id"))
                .achievementCharacteristicId(resultSet.getInt("achievement_characteristic_id"))
                .build();
    }
}
