package com.qucat.quiz.services;

import com.qucat.quiz.repositories.dao.implementation.CategoryDaoImpl;
import com.qucat.quiz.repositories.entities.Category;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;


@Slf4j
@Service
public class CategoryService {
    @Autowired
    private CategoryDaoImpl categoryDao;

    public Category getCategoryById(@RequestParam int id) {
        return categoryDao.getById(id);
    }
}
