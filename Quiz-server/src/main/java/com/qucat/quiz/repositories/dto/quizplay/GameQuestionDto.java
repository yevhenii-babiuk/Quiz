package com.qucat.quiz.repositories.dto.quizplay;

import com.qucat.quiz.repositories.entities.Question;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class GameQuestionDto {
    private int id;
    private Question question;
    private boolean isCurrent;
    private Timestamp finishTime;
}
