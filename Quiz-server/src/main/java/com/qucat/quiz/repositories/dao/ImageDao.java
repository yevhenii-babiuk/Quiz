package com.qucat.quiz.repositories.dao;

import com.qucat.quiz.repositories.entities.Image;

public interface ImageDao extends GenericDao<Image> {
    String TABLE_NAME = "image";
}
