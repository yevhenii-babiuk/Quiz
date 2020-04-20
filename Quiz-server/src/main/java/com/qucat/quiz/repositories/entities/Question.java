package com.qucat.quiz.repositories.entities;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Question {
    private int id;
    private int quizId;
    private QuestionType type;
    private String content;
    private int score;
    private String imageUrl;
    private List<QuestionOption> options;
}
