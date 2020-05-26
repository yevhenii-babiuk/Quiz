package com.qucat.quiz.services;

import com.qucat.quiz.repositories.dao.AchievementDao;
import com.qucat.quiz.repositories.entities.Achievement;
import com.qucat.quiz.repositories.entities.AchievementCondition;
import com.qucat.quiz.repositories.entities.enums.ConditionOperator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class AchievementServiceTest {


    @Captor
    ArgumentCaptor<List<AchievementCondition>> toInsert;
    @Captor
    ArgumentCaptor<List<AchievementCondition>> toDelete;

    List<AchievementCondition> toInsertExpected = new ArrayList<>();

    List<AchievementCondition> toDeleteExpected = new ArrayList<>();

    Achievement achievementBeforeUpdate;

    Achievement achievementAfterUpdate;

    @InjectMocks
    private AchievementService achievementService;

    @Mock
    private UserAchievementsService userAchievementsService;

    @Mock
    private AchievementConditionService achievementConditionService;

    @Mock
    private AchievementDao achievementDao;

    public void setValues() {
        AchievementCondition noChangeCondition = AchievementCondition.builder()
                .characteristicId(1)
                .value(1)
                .achievementId(1)
                .operator(ConditionOperator.EQUALS)
                .build();

        AchievementCondition changedConditionBeforeUpdate = AchievementCondition.builder()
                .characteristicId(2)
                .value(2)
                .achievementId(2)
                .operator(ConditionOperator.GREATER)
                .build();

        AchievementCondition changedConditionAfterUpdate = AchievementCondition.builder()
                .characteristicId(3)
                .value(3)
                .achievementId(3)
                .operator(ConditionOperator.LESS)
                .build();

        AchievementCondition deletedCondition = AchievementCondition.builder()
                .characteristicId(4)
                .value(4)
                .achievementId(4)
                .operator(ConditionOperator.GREATER)
                .build();

        AchievementCondition newCondition = AchievementCondition.builder()
                .characteristicId(5)
                .value(5)
                .achievementId(5)
                .operator(ConditionOperator.EQUALS)
                .build();

        List<AchievementCondition> conditionsBefore = new ArrayList<>();
        conditionsBefore.add(noChangeCondition);
        conditionsBefore.add(changedConditionBeforeUpdate);
        conditionsBefore.add(deletedCondition);

        List<AchievementCondition> conditionsAfter = new ArrayList<>();
        conditionsAfter.add(noChangeCondition);
        conditionsAfter.add(changedConditionAfterUpdate);
        conditionsAfter.add(newCondition);


        toInsertExpected.add(changedConditionAfterUpdate);
        toInsertExpected.add(newCondition);

        toDeleteExpected.add(deletedCondition);
        toDeleteExpected.add(changedConditionBeforeUpdate);

        achievementBeforeUpdate = Achievement.builder()
                .id(7)
                .conditions(conditionsBefore)
                .build();

        achievementAfterUpdate = Achievement.builder()
                .id(achievementBeforeUpdate.getId())
                .conditions(conditionsAfter)
                .build();
    }

    @Before
    public void initMocks() {
        setValues();
        Mockito.when(achievementDao.get(achievementBeforeUpdate.getId())).thenReturn(achievementBeforeUpdate);

    }

    @Test
    public void updateAchievement() {
        assertTrue(achievementService.updateAchievement(achievementAfterUpdate));

        Mockito.verify(achievementConditionService).addConditions(toInsert.capture());//set values into toInsert
        Mockito.verify(achievementConditionService).removeConditions(toDelete.capture());

        assertTrue(toDelete.getValue().containsAll(toDeleteExpected) && toDeleteExpected.containsAll(toDelete.getValue()));//equals without order
        assertTrue(toInsert.getValue().containsAll(toInsertExpected) && toInsertExpected.containsAll(toInsert.getValue()));
    }

    @Test
    public void updateUserAchievementLists() {
    }
}