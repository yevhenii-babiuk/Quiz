package com.qucat.quiz.controllers;

import com.qucat.quiz.repositories.entities.Quiz;
import com.qucat.quiz.services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;

@RestController
@RequestMapping("api/v1/quizzes")
public class QuizListController {

    @Autowired
    private QuizService quizService;

    @GetMapping
    public Quiz[] getQuizzes(
            @RequestParam(value = "category", required = false) String[] categories,
            @RequestParam(value = "tag", required = false) String[] tags,
            @RequestParam(value = "count") int currentCount,
            @RequestParam(value = "quizName") String quizName,
            @RequestParam(value = "authorName") String authorName,
            @RequestParam(value = "date")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)Date[] dates) {
        Timestamp t1 = null, t2 = null;
        return quizService.showPage(currentCount, 20,
                quizName, authorName, Arrays.asList(categories), t1, t2, Arrays.asList(tags)).toList().toArray(Quiz[]::new);

    }

}
