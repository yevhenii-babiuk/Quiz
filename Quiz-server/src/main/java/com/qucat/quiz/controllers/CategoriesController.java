package com.qucat.quiz.controllers;

import com.qucat.quiz.repositories.entities.Category;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/categories")
public class CategoriesController {

    @GetMapping
    public Category[] getCategories() {
        List<Category> categories = new ArrayList<>();
        categories.add(Category.builder().name("Category1").build());
        categories.add(Category.builder().name("Category2").build());
        categories.add(Category.builder().name("Category3").build());
        categories.add(Category.builder().name("Category4").build());
        return categories.toArray(Category[]::new);
    }

}
