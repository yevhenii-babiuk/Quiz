package com.qucat.quiz.repositories.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class Message {
    private int id;
    private int chatId;
    private int authorId;
    private String authorLogin;
    private Date creationDate;
    private boolean isViewed;
    private String messageText;
}