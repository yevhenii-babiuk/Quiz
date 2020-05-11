package com.qucat.quiz.repositories.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;


@Data
@Builder
@AllArgsConstructor
public class UserAchievement {
    private int userId;
    private User user;
    private int achievementId;
    private Achievement achievement;
    private Date date;
}
