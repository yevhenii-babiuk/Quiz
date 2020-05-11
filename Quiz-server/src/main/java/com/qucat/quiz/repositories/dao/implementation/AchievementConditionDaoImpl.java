package com.qucat.quiz.repositories.dao.implementation;

import com.qucat.quiz.repositories.dao.AchievementConditionDao;
import com.qucat.quiz.repositories.dao.mappers.AchievementConditionMapper;
import com.qucat.quiz.repositories.entities.AchievementCondition;
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
public class AchievementConditionDaoImpl extends GenericDaoImpl<AchievementCondition> implements AchievementConditionDao {

    @Value("#{${sql.achievementCondition}}")
    private Map<String, String> achievementConditionQueries;

    protected AchievementConditionDaoImpl() {
        super(new AchievementConditionMapper(), TABLE_NAME);
    }

    @Override
    protected String getInsertQuery() {
        return achievementConditionQueries.get("insert");
    }

    @Override
    protected PreparedStatement getInsertPreparedStatement(PreparedStatement preparedStatement, AchievementCondition achievementCondition) throws SQLException {
        preparedStatement.setString(1, achievementCondition.getOperator().name().toLowerCase());
        preparedStatement.setInt(2, achievementCondition.getValue());
        preparedStatement.setInt(3, achievementCondition.getAchievementId());
        preparedStatement.setInt(4, achievementCondition.getCharacteristicId());
        return preparedStatement;
    }

    @Override
    protected String getUpdateQuery() {
        return achievementConditionQueries.get("update");
    }

    @Override
    protected Object[] getUpdateParameters(AchievementCondition achievementCondition) {
        return new Object[]{achievementCondition.getOperator().name().toLowerCase(),achievementCondition.getValue(),
                achievementCondition.getAchievementId(), achievementCondition.getCharacteristicId(), achievementCondition.getId()};
    }
}
