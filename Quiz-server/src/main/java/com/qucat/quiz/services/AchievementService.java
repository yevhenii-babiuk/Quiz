package com.qucat.quiz.services;

import com.qucat.quiz.repositories.dao.AchievementDao;
import com.qucat.quiz.repositories.entities.Achievement;
import com.qucat.quiz.repositories.entities.AchievementCondition;
import com.qucat.quiz.repositories.entities.UserAchievement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
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

    @Autowired
    private UserAchievementsService userAchievementsService;

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
        achievementDao.deleteById(achievement.getId());
    }

    public List<Achievement> getAllAchievement() {
        return achievementDao.getAll();
    }

    @Scheduled(fixedDelay = 3600_000)
    public synchronized void updateUserAchievementLists() {
        List<Achievement> achievements = getAllAchievement();
        List<UserAchievement> beforeUpdateAchievements = userAchievementsService.getAchievementsForAll();
        List<UserAchievement> afterUpdateAchievements = achievementDao.getNewUserAchievements(achievements);

        if (afterUpdateAchievements.equals(beforeUpdateAchievements)) {
            log.info("here is no changes in userAchievementLists");
            return;
        }

        List<UserAchievement> toInsert = afterUpdateAchievements.stream()
                .filter(userAchievement -> !beforeUpdateAchievements.contains(userAchievement))
                .collect(Collectors.toList());
        List<UserAchievement> toDelete = beforeUpdateAchievements.stream()
                .filter(userAchievement -> !afterUpdateAchievements.contains(userAchievement))
                .collect(Collectors.toList());

        log.info("toInsert: " + toInsert);
        log.info("toDelete: " + toDelete);

        if (!toDelete.isEmpty()) {
            userAchievementsService.deleteUserAchievements(toDelete);
        }
        if (!toInsert.isEmpty()) {
            userAchievementsService.insertUserAchievements(toInsert);
        }

    }

}
