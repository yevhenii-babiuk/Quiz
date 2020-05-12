package com.qucat.quiz.repositories.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class Activity {
    private int id;
    private String friendLogin;
    private String achievementName;
    private String quizName;
    private String quizCategoryName;
    private boolean markedFavourite;
    private String friendOfFriendLogin;
    private Date activityDate;
    private int imageId;
    private Image image;
}
