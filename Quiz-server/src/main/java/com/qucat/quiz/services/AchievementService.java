package com.qucat.quiz.services;

import com.qucat.quiz.repositories.dao.AchievementDao;
import com.qucat.quiz.repositories.entities.Achievement;
import com.qucat.quiz.repositories.entities.AchievementCondition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AchievementService {

    @Autowired
    private AchievementDao achievementDao;

    @Autowired
    private AchievementConditionService achievementConditionService;


    @Transactional
    public boolean createAchievement(Achievement achievement) {
        if (achievement == null) {
            log.info("cant createAchievement: achievement is null");
            return false;
        }
        if (achievement.getConditions().isEmpty()) {
            log.info("cant createAchievement: achievement.conditions is null");
            return false;
        }

        int achievementId = achievementDao.save(achievement);

        for (AchievementCondition achievementCondition : achievement.getConditions()) {
            achievementCondition.setAchievementId(achievementId);
        }

        achievementConditionService.addConditions(achievement.getConditions());

        return true;
    }

    @Transactional
    public boolean updateAchievement(Achievement achievement) {
        if (achievement == null || achievement.getConditions().isEmpty()) {
            log.info("cant updateAchievement: achievement is null");
            return false;
        }

        Achievement achievementBeforeUpdate = achievementDao.get(achievement.getId());
        if (achievementBeforeUpdate == null) {
            log.info("cant updateAchievement: achievementBeforeUpdate is null");
            return false;
        }

        if (achievement.equals(achievementBeforeUpdate)) {
            return true;
        }

        List<AchievementCondition> afterUpdateConditions = achievement.getConditions();
        List<AchievementCondition> beforeUpdateConditions = achievementBeforeUpdate.getConditions();

        List<AchievementCondition> toInsert = afterUpdateConditions.stream()
                .filter(condition -> !beforeUpdateConditions.contains(condition))
                .collect(Collectors.toList());

        List<AchievementCondition> toDelete = beforeUpdateConditions.stream()
                .filter(condition -> !afterUpdateConditions.contains(condition))
                .collect(Collectors.toList());

        achievementConditionService.removeConditions(toDelete);
        achievementConditionService.addConditions(toInsert);

        return true;
    }

    @Transactional
    public void deleteAchievement(Achievement achievement) {
        achievementDao.delete(achievement);
    }

    public List<Achievement> getAllAchievement() {
        return achievementDao.getAll();
    }

}
