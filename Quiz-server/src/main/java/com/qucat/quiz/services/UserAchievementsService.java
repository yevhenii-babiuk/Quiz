package com.qucat.quiz.services;

import com.qucat.quiz.repositories.dao.UserAchievementsDao;
import com.qucat.quiz.repositories.entities.Achievement;
import com.qucat.quiz.repositories.entities.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserAchievementsService {

    @Autowired
    private UserAchievementsDao userAchievementsDao;

    public List<Achievement> getAchievementsForAll() {
        return userAchievementsDao.getAchievementsForAllUser();
    }

    public List<Achievement> getAchievementsByUserId(int userId) {
        return userAchievementsDao.getAchievementsByUserId(userId);
    }

}
