package com.qucat.quiz.repositories.dto;

import com.qucat.quiz.repositories.entities.Question;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class AnswerDto {
    private int id;
    private int userId;
    private UserDto user;
    private int percent;
    private int questionId;
    private Question question;
    private boolean isCorrect;
    private Timestamp time;
}
