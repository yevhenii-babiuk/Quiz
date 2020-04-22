package com.qucat.quiz.controllers;

import com.qucat.quiz.repositories.entities.Quiz;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/quiz")
public class QuizController {

    @PutMapping
    public boolean registerUser(@RequestBody Quiz quiz) {
        System.out.println(quiz);
       return true;
    }
}
