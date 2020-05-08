package com.qucat.quiz.controllers;

import com.qucat.quiz.repositories.entities.Quiz;
import com.qucat.quiz.repositories.entities.QuizStatus;
import com.qucat.quiz.services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.List;


@RestController
@RequestMapping("api/v1/quizzes")
public class QuizListController {

    @Autowired
    private QuizService quizzesService;


    @GetMapping
    public Page<Quiz> getPage(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(required = false, defaultValue = "10") int size,
                              @RequestParam(required = false) String name,
                              @RequestParam(required = false) String author,
                              @RequestParam(required = false) List<String> category,
                              @RequestParam(required = false) Timestamp minDate,
                              @RequestParam(required = false) Timestamp maxDate,
                              @RequestParam(required = false) List<String> tags,
                              @RequestParam(required = false) QuizStatus status) {
        return quizzesService.showPage(page, size, name, author, category, minDate, maxDate, tags, status);
    }

}
