package com.qucat.quiz.repositories.entities;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Notification {
    private int id;
    private int userId;
    private String author;
    private String action;
    private String authorLink;
    private String actionLink;
    @JsonProperty("isViewed")
    private boolean isViewed;
}
