package com.qucat.quiz.controllers;

import com.qucat.quiz.repositories.entities.Category;
import com.qucat.quiz.repositories.entities.Quiz;
import com.qucat.quiz.repositories.entities.enums.QuizStatus;
import com.qucat.quiz.repositories.entities.Tag;
import com.qucat.quiz.services.CategoryService;
import com.qucat.quiz.services.QuizService;
import com.qucat.quiz.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

@RestController
@RequestMapping("api/v1")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;


    @PostMapping("/quiz")
    public boolean addQuiz(@RequestBody Quiz quiz) {
        return quizService.createQuiz(quiz);
    }

    @PutMapping("/quiz")
    public void updateQuiz(@RequestBody Quiz quiz) {
        quizService.updateQuiz(quiz);
    }

    @GetMapping("/quiz/{id}")
    public Quiz getQuiz(@PathVariable int id) {
        return quizService.getQuizById(id);
    }

    @GetMapping("/quizzes")
    public Quiz[] getQuizzes(
            @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(value = "countOnPage", defaultValue = "20") int countOnPage,
            @RequestParam(value = "category", required = false) ArrayList<String> categories,
            @RequestParam(value = "tag", required = false) ArrayList<String> tags,
            @RequestParam(value = "quizName", required = false) String quizName,
            @RequestParam(value = "authorName", required = false) String authorName,
            @RequestParam(value = "minDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date minDate,
            @RequestParam(value = "maxDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date maxDate,
            @RequestParam(value = "status", required = false) QuizStatus[] statuses) {
        return quizService.showPage(pageNumber, countOnPage, quizName, authorName,
                categories, minDate, maxDate, tags, statuses).toList().toArray(Quiz[]::new);
    }

    @GetMapping("/userFavoriteQuizzes")
    public Quiz[] getUserFavoriteQuizzes(
            @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(value = "countOnPage", defaultValue = "20") int countOnPage,
            @RequestParam(value = "userId") int userId) {
        Quiz[] quizzes = new Quiz[countOnPage];
        Arrays.fill(quizzes, quizService.getQuizById(22));
        return quizzes;
    }

    @GetMapping("/userCompletedQuizzes")
    public Quiz[] getUserCompletedQuizzes(
            @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(value = "countOnPage", defaultValue = "20") int countOnPage,
            @RequestParam(value = "userId") int userId) {
        Quiz[] quizzes = new Quiz[countOnPage];
        Arrays.fill(quizzes, quizService.getQuizById(21));
        return quizzes;
    }

    @GetMapping("/userCreatedQuizzes")
    public Quiz[] getUserQuizzes(
            @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(value = "countOnPage", defaultValue = "20") int countOnPage,
            @RequestParam(value = "userId") int userId) {
        Quiz[] quizzes = new Quiz[countOnPage];
        Arrays.fill(quizzes, quizService.getQuizById(20));
        return quizzes;
    }

    @GetMapping("/categories")
    public Category[] getCategories() {
        return categoryService.getAllCategories().toArray(Category[]::new);
    }

    @GetMapping("/tags")
    public Tag[] getTags() {
        return tagService.getAllTags().toArray(Tag[]::new);
    }

}
