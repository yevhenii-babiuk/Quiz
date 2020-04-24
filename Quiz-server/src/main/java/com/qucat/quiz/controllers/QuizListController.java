package com.qucat.quiz.controllers;

import com.qucat.quiz.repositories.entities.Quiz;
import com.qucat.quiz.services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/quizzes")
public class QuizListController {

    @Autowired
    private QuizService quizService;

    @GetMapping
    public Quiz[] getQuizzes(@RequestParam(value = "count") int currentCount) {
        return quizService.showPage(Optional.of(currentCount), Optional.of(20)).toList().toArray(Quiz[]::new);

    }

}
