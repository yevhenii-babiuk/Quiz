package com.qucat.quiz.controllers;

import com.qucat.quiz.repositories.entities.Category;
import com.qucat.quiz.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class CategoriesController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    public Category[] getCategories() {
        return categoryService.getAllCategories().toArray(Category[]::new);
    }

}
