package com.qucat.quiz.repositories.dto.quizplay;

import com.qucat.quiz.repositories.entities.Image;
import com.qucat.quiz.repositories.entities.Question;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class QuizDto {
    private int id;
    private String name;
    private int questionNumber;
    private int imageId;
    private Image image;
    private List<Question> questions;
}
