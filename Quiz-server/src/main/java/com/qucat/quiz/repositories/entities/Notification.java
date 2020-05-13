package com.qucat.quiz.repositories.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    private int id;
    private int userId;
    private String author;
    private String action;
    private String authorLink;
    private String actionLink;
    private boolean isViewed;

    public void setIsViewed(boolean b) {
    }
}
