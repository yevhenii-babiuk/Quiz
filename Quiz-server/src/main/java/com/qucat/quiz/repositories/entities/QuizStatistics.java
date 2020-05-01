package com.qucat.quiz.repositories.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuizStatistics {
    private String name;
    private double value;
}
