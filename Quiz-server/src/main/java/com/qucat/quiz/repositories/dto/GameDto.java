package com.qucat.quiz.repositories.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GameDto {

    private String gameId;
    private int quizId;
    private int time;
    private boolean questionAnswerSequence;
    private boolean quickAnswerBonus;
    private boolean combo;
    private boolean intermediateResult;
    private QuizDto quiz;
    private int hostId;
    private String image;
}
