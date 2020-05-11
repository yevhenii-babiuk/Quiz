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

public class UserAchievementExtractor implements ResultSetExtractor<List<User>> {

    @Override
    public List<User> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Map<Integer, User> users = new HashMap<>();
        while (resultSet.next()) {
            int userId = resultSet.getInt("user_id");
            User user = users.get(userId);
            if (user == null) {
                user = User.builder()
                        .id(userId)
                        .achievements(new ArrayList<>())
                        .build();
                users.put(userId, user);
            }
            user.getAchievements().add(Achievement.builder()
                    .description(resultSet.getString("description"))
                    .name(resultSet.getString("achievement_name"))
                    .id(resultSet.getInt("achievement_id"))
                    .build());
        }

        return new ArrayList<>(users.values());
    }
}
