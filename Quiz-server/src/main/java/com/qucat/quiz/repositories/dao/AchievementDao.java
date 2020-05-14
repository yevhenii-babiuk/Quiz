package com.qucat.quiz.repositories.dao;

import com.qucat.quiz.repositories.entities.Achievement;
import com.qucat.quiz.repositories.entities.UserAchievement;

import java.util.List;

public interface AchievementDao extends GenericDao<Achievement> {
    String TABLE_NAME = "achievement";

    List<UserAchievement> getNewUserAchievements(List<Achievement> achievements);
}
