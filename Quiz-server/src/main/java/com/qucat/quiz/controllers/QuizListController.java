package com.qucat.quiz.controllers;

import com.qucat.quiz.repositories.entities.Quiz;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/v1/quizzes")
public class QuizListController {

    @GetMapping
    public Quiz[] getQuizzes(@RequestParam(value = "count") int currentCount) {
        return null;//quizService.showPage(Optional.of(currentCount), Optional.of(20)).toList().toArray(Quiz[]::new);

    }

}
