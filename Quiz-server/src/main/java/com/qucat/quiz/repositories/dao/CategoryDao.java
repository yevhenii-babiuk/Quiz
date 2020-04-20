package com.qucat.quiz.repositories.dao;

import com.qucat.quiz.repositories.entities.Category;

public interface CategoryDao extends GenericDao<Category> {
    String TABLE_NAME = "category";
}
