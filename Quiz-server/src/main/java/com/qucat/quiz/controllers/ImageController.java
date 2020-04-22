package com.qucat.quiz.controllers;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;

@RestController
@RequestMapping("api/v1/image")
public class ImageController {

    @PutMapping
    public int saveImage(@RequestParam("myFile") MultipartFile file) throws IOException {
        System.out.println("saveImage");
        System.out.println(file.getName());
        byte[] bytes = file.getBytes();
        System.out.println(Arrays.toString(bytes));
        return bytes.length;
    }
}
