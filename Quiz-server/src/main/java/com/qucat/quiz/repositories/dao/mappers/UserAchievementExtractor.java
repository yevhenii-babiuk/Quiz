package com.qucat.quiz.repositories.dao.mappers;

import com.qucat.quiz.repositories.entities.Achievement;
import com.qucat.quiz.repositories.entities.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserAchievementExtractor implements ResultSetExtractor<List<Achievement>> {

    @Override
    public List<Achievement> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Map<Integer, Achievement> achievements = new HashMap<>();
        while (resultSet.next()) {
            int achievementId = resultSet.getInt("achievement_id");
            Achievement achievement = achievements.get(achievementId);
            if (achievement == null) {
                achievement = Achievement.builder()
                        .id(achievementId)
                        .name(resultSet.getString("achievement_name"))
                        .users(new ArrayList<>())
                        .description(resultSet.getString("description"))
                        .build();
                achievements.put(achievementId, achievement);
            }

            achievement.getUsers().add(User.builder()
                    .userId(resultSet.getInt("user_id"))
                    .build());


        }

        return new ArrayList<>(achievements.values());
    }
}
