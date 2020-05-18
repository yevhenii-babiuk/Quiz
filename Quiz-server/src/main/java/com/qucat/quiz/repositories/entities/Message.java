package com.qucat.quiz.repositories.entities;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class Message {
    private int id;
    private int chatId;
    private int authorId;
    private User author;
    private Date creationDate;
    private String messageText;
}
