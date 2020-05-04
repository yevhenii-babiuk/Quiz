package com.qucat.quiz.repositories.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
public class Quiz {
    private int id;
    private String name;
    private int authorId;
    private int categoryId;
    private QuizStatus status;
    private Timestamp publishedDate;
    private Timestamp updatedDate;
    private Timestamp createdDate;
    private int questionNumber;
    private int maxScore;
    private int imageId;
    private List<Question> questions;
    private Category category;
    private List<Tag> tags;
    private Image image;
    @JsonProperty("isFavorite")
    private boolean isFavorite;
}