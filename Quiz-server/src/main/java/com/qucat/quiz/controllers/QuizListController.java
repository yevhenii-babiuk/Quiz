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
            @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(value = "countOnPage", defaultValue = "20") int countOnPage,
            @RequestParam(value = "category", required = false) String[] categories,
            @RequestParam(value = "tag", required = false) String[] tags,
            @RequestParam(value = "quizName", required = false) String quizName,
            @RequestParam(value = "authorName", required = false) String authorName,
            @RequestParam(value = "date", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)Date[] dates) {
        Timestamp t1 = null, t2 = null;
        return quizService.showPage(pageNumber, countOnPage,
                quizName, authorName, Arrays.asList(categories), t1, t2, Arrays.asList(tags)).toList().toArray(Quiz[]::new);

    }

}
