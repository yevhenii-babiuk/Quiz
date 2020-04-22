package com.qucat.quiz.controllers;

import com.qucat.quiz.repositories.entities.Quiz;
import com.qucat.quiz.services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/quiz")
public class QuizController {

    @Autowired
    QuizService quizService;

    @PostMapping
    public boolean addQuiz(@RequestBody Quiz quiz) {
        return quizService.createQuiz(quiz);
    }

    @PutMapping
    public void updateQuiz(@RequestBody Quiz quiz) {
        quizService.updateQuiz(quiz);
    }

    @GetMapping("{id}")
    public Quiz getQuiz(@PathVariable int id) {
        return quizService.getQuizById(id);
    }
}
