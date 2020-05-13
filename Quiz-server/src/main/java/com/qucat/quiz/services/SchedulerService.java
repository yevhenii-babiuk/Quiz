package com.qucat.quiz.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
@Slf4j
public class SchedulerService {

    @Autowired
    private AchievementService achievementService;

    @Autowired
    private TokenService tokenService;

    @Scheduled(cron = "0 0 * * * *")
    public void reportCurrentTime() {
        tokenService.deleteOldTokens();
        achievementService.updateUserAchievementLists();
    }

}
