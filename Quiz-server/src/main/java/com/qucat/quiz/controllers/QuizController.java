package com.qucat.quiz.controllers;

import com.qucat.quiz.repositories.entities.*;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/quiz")
public class QuizController {

    @PutMapping
    public boolean setQuiz(@RequestBody Quiz quiz) {
        System.out.println(quiz);
        return true;
    }

    @GetMapping("{id}")
    public Quiz getQuiz(@PathVariable int id) {
        List<QuestionOption> questionOptions = new ArrayList<>();
        questionOptions.add(QuestionOption.builder().isCorrect(true).build());
        List<Question> questionList = new ArrayList<>();
        questionList.add(Question.builder().content("content").type(QuestionType.TRUE_FALSE)
                .options(questionOptions).build());
        return Quiz.builder()
                .id(id)
                .name("QName")
                .category(Category.builder().name("Category3").id(3).build())
                .questions(questionList)
                .build();
    }
}
