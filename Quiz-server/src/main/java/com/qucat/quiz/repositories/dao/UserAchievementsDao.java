package com.qucat.quiz.repositories.dao;

import com.qucat.quiz.repositories.entities.Achievement;
import com.qucat.quiz.repositories.entities.User;
import com.qucat.quiz.repositories.entities.UserAchievement;

import java.util.List;

public interface UserAchievementsDao extends GenericDao<UserAchievement> {
    String TABLE_NAME = "user_achievements_list";

    List<Achievement> getAchievementsByUserId(int userId);

    List<Achievement> getAchievementsForAllUser();
}
