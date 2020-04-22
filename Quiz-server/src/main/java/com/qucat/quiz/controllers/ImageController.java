package com.qucat.quiz.controllers;

import com.qucat.quiz.services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/image")
public class ImageController {

    @Autowired
    QuizService quizService;

    @PutMapping
    public int saveImage(@RequestParam("myFile") MultipartFile file) {
        return quizService.addImage(file);
    }
}
