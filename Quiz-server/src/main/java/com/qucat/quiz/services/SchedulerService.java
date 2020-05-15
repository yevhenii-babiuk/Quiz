package com.qucat.quiz.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SchedulerService {

    @Autowired
    private AchievementService achievementService;

    @Autowired
    private TokenService tokenService;

    @Scheduled(cron = "0 0 * * * *")
    public void deleteOldTokens() {
        tokenService.deleteOldTokens();
    }

    @Scheduled(cron = "0 0 * * * *")
    public void updateUserAchievementLists() {
        achievementService.updateUserAchievementLists();
    }

}
