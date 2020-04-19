package com.qucat.quiz.repositories.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuestionOption {
    private int id;
    private int questionId;
    private String title;
}
