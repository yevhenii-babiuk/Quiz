package com.qucat.quiz.repositories.dto.game;

import com.qucat.quiz.repositories.entities.Question;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class GameQuestionDto {
    private int id;
    private String gameId;
    private GameDto game;
    private int questionId;
    private Question question;
    private boolean isCurrent;
    private Timestamp finishTime;
}
