package com.qucat.quiz.repositories.entities;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class Quiz {
    private int id;
    private String name;
    private int authorId;
    private Category category;
    private QuizStatus status;
    private Date publishedDate;
    private Date updatedDate;
    private Date createdDate;
    private int questionNumber;
    private int maxScore;
    private List<Question> questions;
    private List<Tag> tags;
}
