package com.qucat.quiz.controllers;

import com.qucat.quiz.repositories.entities.Achievement;
import com.qucat.quiz.repositories.entities.AchievementCharacteristic;
import com.qucat.quiz.services.AchievementCharacteristicService;
import com.qucat.quiz.services.AchievementService;
import com.qucat.quiz.services.UserAchievementsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/")
public class AchievementController {

    @Autowired
    private AchievementService achievementService;

    @Autowired
    private UserAchievementsService userAchievementsService;

    @Autowired
    private AchievementCharacteristicService achievementCharacteristicService;

    @GetMapping("/profile/{id}/achievements/")
    public List<Achievement> getUserAchievement(@PathVariable int id) {
        return userAchievementsService.getAchievementsByUserId(id);
    }

    @GetMapping("/achievement/characteristics")
    public List<AchievementCharacteristic> getAchievementCharacteristics() {
        return achievementCharacteristicService.getAll();
    }

    @PostMapping("/achievement/create")
    public boolean createAchievement(@RequestBody Achievement achievement) {
        return achievementService.createAchievement(achievement);
    }

    @PutMapping("/achievement/recalculate")
    public void recalculateUserAchievements() {
        achievementService.updateUserAchievementLists();
    }
}
