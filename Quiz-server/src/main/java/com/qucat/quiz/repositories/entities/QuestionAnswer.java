package com.qucat.quiz.repositories.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuestionAnswer {
    private int id;
    private int questionOptionId;
    private int sequenceOrder;
    private String answer;
}
