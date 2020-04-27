package com.qucat.quiz.repositories.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

@Data
@AllArgsConstructor
public class AdminStatistics {
    private Date date;
    private int createdCount;
    private int publishedCount;
}
