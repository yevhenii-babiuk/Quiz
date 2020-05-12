package com.qucat.quiz.repositories.dao;

import com.qucat.quiz.repositories.entities.Achievement;

public interface AchievementDao extends GenericDao<Achievement> {
    String TABLE_NAME = "achievement";
}
