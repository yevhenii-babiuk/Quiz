package com.qucat.quiz.services;

import com.qucat.quiz.repositories.dao.AchievementConditionDao;
import com.qucat.quiz.repositories.entities.AchievementCondition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AchievementConditionService {

    @Autowired
    private AchievementConditionDao achievementConditionDao;

    public void addConditions(List<AchievementCondition> conditions) {
        for (AchievementCondition condition : conditions) {
            addCondition(condition);//todo change
        }
    }

    public int addCondition(AchievementCondition achievementCondition) {
        return achievementConditionDao.save(achievementCondition);
    }

    public void removeConditions(List<AchievementCondition> conditions) {
        for (AchievementCondition condition : conditions) {
            removeCondition(condition);//todo change
        }
    }

    public void removeCondition(AchievementCondition achievementCondition) {
        achievementConditionDao.delete(achievementCondition);
    }
}
