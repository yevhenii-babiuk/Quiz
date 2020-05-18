package com.qucat.quiz.repositories.entities;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class Chat {
    private int id;
    private String name;
    private Date creationDate;
    private List<User> users;
}
