package com.qucat.quiz.controllers;

import com.qucat.quiz.repositories.entities.Quiz;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/quizzes")
public class QuizListController {

    @GetMapping("{currentCount}")
    public Quiz[] getQuizzes(@PathVariable int currentCount) {
        System.out.println("currentCount = " + currentCount);
        List<Quiz> quizList = new ArrayList<>();
        for (int i = currentCount; i < currentCount + 10; i++) {
            quizList.add(Quiz.builder().name("name").id(i).build());
            quizList.add(Quiz.builder().name("name").id(i + 2000).build());
        }
        return quizList.toArray(Quiz[]::new);
    }

}
