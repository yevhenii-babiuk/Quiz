package com.qucat.quiz.controllers;

import com.qucat.quiz.services.ImageService;
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
    private ImageService imageService;

    @PutMapping
    public int saveImage(@RequestParam("myFile") MultipartFile file) {
        return imageService.addImage(file);
    }

    @PutMapping("/profile")
    public int saveImageForProfile(@RequestParam("myFile") MultipartFile file) {
        System.out.println("loaded");
        return 0;
        //return imageService.addImage(file);
    }
}
