package com.qucat.quiz.controllers;

import com.qucat.quiz.repositories.entities.Category;
import com.qucat.quiz.repositories.entities.Quiz;
import com.qucat.quiz.repositories.entities.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/quizzes")
public class QuizListController {

    @GetMapping("{currentCount}")
    public Quiz[] getQuizzes(@PathVariable int currentCount) {
        System.out.println("currentCount = " + currentCount);
        List<Tag> tags = new ArrayList<>();
        tags.add(Tag.builder().name("tag1").build());
        tags.add(Tag.builder().name("tag2").build());
        tags.add(Tag.builder().name("tag3").build());
        tags.add(Tag.builder().name("tag4").build());
        tags.add(Tag.builder().name("tag5").build());
        tags.add(Tag.builder().name("tag6").build());
        tags.add(Tag.builder().name("tag7").build());
        tags.add(Tag.builder().name("tag8").build());
        List<Quiz> quizList = new ArrayList<>();
        Category category = Category.builder().id(1).name("category").build();
        for (int i = currentCount; i < currentCount + 20; i++) {
            quizList.add(Quiz.builder().name("name").id(i).tags(tags).category(category).maxScore(25).build());
        }
        return quizList.toArray(Quiz[]::new);
    }

}
