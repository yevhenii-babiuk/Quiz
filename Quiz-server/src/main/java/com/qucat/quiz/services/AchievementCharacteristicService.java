package com.qucat.quiz.services;

import com.qucat.quiz.repositories.dao.AchievementCharacteristicDao;
import com.qucat.quiz.repositories.entities.AchievementCharacteristic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AchievementCharacteristicService {

    @Autowired
    private AchievementCharacteristicDao achievementCharacteristicDao;

    public List<AchievementCharacteristic> getAll() {
        return achievementCharacteristicDao.getAll();
    }
}
