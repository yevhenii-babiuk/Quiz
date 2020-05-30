package com.qucat.quiz.services;

import com.qucat.quiz.repositories.dao.AchievementDao;
import com.qucat.quiz.repositories.entities.Achievement;
import com.qucat.quiz.repositories.entities.AchievementCondition;
import com.qucat.quiz.repositories.entities.UserAchievement;
import com.qucat.quiz.repositories.entities.enums.ConditionOperator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AchievementServiceTest {


    @Captor
    ArgumentCaptor<List<AchievementCondition>> toInsert;
    @Captor
    ArgumentCaptor<List<AchievementCondition>> toDelete;

    @Captor
    ArgumentCaptor<List<UserAchievement>> toInsertUAL;
    @Captor
    ArgumentCaptor<List<UserAchievement>> toDeleteUAL;

    List<AchievementCondition> toInsertExpected = new ArrayList<>();

    List<AchievementCondition> toDeleteExpected = new ArrayList<>();

    Achievement achievementBeforeUpdate;

    Achievement achievementAfterUpdate;

    List<UserAchievement> userAchievementsBefore = new ArrayList<>();

    List<UserAchievement> userAchievementsAfter = new ArrayList<>();

    UserAchievement noChangeUserAchievement;
    UserAchievement oldUserAchievement;
    UserAchievement newUserAchievement;

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
                .characteristicId(2)
                .value(2)
                .achievementId(2)
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

        noChangeUserAchievement = UserAchievement.builder().userId(1).achievementId(1).build();
        oldUserAchievement = UserAchievement.builder().userId(2).achievementId(2).build();
        newUserAchievement = UserAchievement.builder().userId(3).achievementId(3).build();

        userAchievementsBefore.add(noChangeUserAchievement);
        userAchievementsBefore.add(oldUserAchievement);

        userAchievementsAfter.add(noChangeUserAchievement);
        userAchievementsAfter.add(newUserAchievement);


    }

    @Before
    public void initMocks() {
        setValues();
        when(achievementDao.get(achievementBeforeUpdate.getId())).thenReturn(achievementBeforeUpdate);

    }

    @Test
    public void updateAchievementWithUpdate() {
        assertTrue(achievementService.updateAchievement(achievementAfterUpdate));

        verify(achievementConditionService).addConditions(toInsert.capture());//set values into toInsert
        verify(achievementConditionService).removeConditions(toDelete.capture());

        assertTrue(toDelete.getValue().containsAll(toDeleteExpected) && toDeleteExpected.containsAll(toDelete.getValue()));//equals without order
        assertTrue(toInsert.getValue().containsAll(toInsertExpected) && toInsertExpected.containsAll(toInsert.getValue()));
    }

    @Test
    public void updateAchievementWithoutUpdate() {
        assertTrue(achievementService.updateAchievement(achievementBeforeUpdate));

        verify(achievementConditionService, never()).addConditions(toInsert.capture());
        verify(achievementConditionService, never()).removeConditions(toDelete.capture());
    }

    @Test
    public void updateUserAchievementListsWithoutUpdate() {
        when(userAchievementsService.getAchievementsForAll()).thenReturn(userAchievementsBefore);
        when(achievementDao.getNewUserAchievements(any())).thenReturn(userAchievementsBefore);

        achievementService.updateUserAchievementLists();

        verify(userAchievementsService, never()).deleteUserAchievements(toInsertUAL.capture());
        verify(userAchievementsService, never()).insertUserAchievements(toDeleteUAL.capture());
    }

    @Test
    public void updateUserAchievementListsWithUpdate() {
        when(userAchievementsService.getAchievementsForAll()).thenReturn(userAchievementsBefore);
        when(achievementDao.getNewUserAchievements(any())).thenReturn(userAchievementsAfter);

        achievementService.updateUserAchievementLists();

        verify(userAchievementsService).deleteUserAchievements(toDeleteUAL.capture());
        verify(userAchievementsService).insertUserAchievements(toInsertUAL.capture());

        assertEquals(toInsertUAL.getValue().size(), 1);
        assertEquals(toInsertUAL.getValue().get(0), newUserAchievement);

        assertEquals(toDeleteUAL.getValue().size(), 1);
        assertEquals(toDeleteUAL.getValue().get(0), oldUserAchievement);

    }
}