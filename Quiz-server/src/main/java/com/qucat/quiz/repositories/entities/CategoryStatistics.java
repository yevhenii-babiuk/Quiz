package com.qucat.quiz.repositories.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class CategoryStatistics {
    private int categoryId;
    private String name;
    private Timestamp takeDate;
    private int count;
}