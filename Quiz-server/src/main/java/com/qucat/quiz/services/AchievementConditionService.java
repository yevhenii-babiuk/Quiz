package com.qucat.quiz.services;

import com.qucat.quiz.repositories.dao.AchievementConditionDao;
import com.qucat.quiz.repositories.entities.AchievementCondition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AchievementConditionService {

    @Autowired
    private AchievementConditionDao achievementConditionDao;

    public void addConditions(List<AchievementCondition> conditions) {
        achievementConditionDao.insert(conditions);
    }

    public int addCondition(AchievementCondition achievementCondition) {
        return achievementConditionDao.save(achievementCondition);
    }

    public void removeConditions(List<AchievementCondition> conditions) {
        achievementConditionDao.delete(conditions.stream()
                .map(AchievementCondition::getId)
                .collect(Collectors.toList()));
    }

    public void removeCondition(AchievementCondition achievementCondition) {
        achievementConditionDao.deleteById(achievementCondition.getAchievementId());
    }
}
