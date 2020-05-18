package com.qucat.quiz.repositories.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class Chat {
    private int id;
    private String name;
    private Date creationDate;
}
