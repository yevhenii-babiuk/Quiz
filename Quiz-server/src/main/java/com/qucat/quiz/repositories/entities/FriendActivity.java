package com.qucat.quiz.repositories.entities;

import com.qucat.quiz.repositories.entities.enums.FriendActivityType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class FriendActivity {
    private int friendId;
    private String friendLogin;
    private String friendImageSrc;
    private int activityId;
    private String activityContent;
    private Date activityDate;
    private FriendActivityType type;
}
