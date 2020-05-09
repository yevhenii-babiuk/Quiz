package com.qucat.quiz.repositories.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AchievementCondition {
    int id;
    private ConditionOperator operator;
    private int value;
    private int achievementId;
    private int achievementCharacteristicId;
    private AchievementCharacteristic characteristic;
}
