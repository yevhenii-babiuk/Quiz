package com.qucat.quiz.repositories.dto.quizPlay;

import com.qucat.quiz.repositories.entities.Question;
import com.qucat.quiz.repositories.entities.UserDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnswerDto {
    private int id;
    private int userId;
    private UserDto user;
    private String answer;
    private int questionId;
    private Question question;
    private boolean isCorrect;
    private int time;
}
