package com.qucat.quiz.repositories.dto.quizplay;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GameDto {

    private int gameId;
    private int quizId;
    private int time;
    private boolean questionAnswerSequence;
    private boolean quickAnswerBonus;
    private boolean combo;
    private boolean intermediateResult;
    private QuizDto quiz;
    private int hostId;
}
